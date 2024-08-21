package com.beapps.assessment_task_trycar.post.data.repository

import com.beapps.assessment_task_trycar.post.data.local.PostsDb
import com.beapps.assessment_task_trycar.post.data.remote.PostsApi
import com.beapps.assessment_task_trycar.post.domain.models.Post
import com.beapps.assessment_task_trycar.post.domain.models.PostComment
import com.beapps.assessment_task_trycar.post.domain.repository.PostsRepository
import com.beapps.assessment_task_trycar.post.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostsRepositoryImpl(
    private val api: PostsApi,
    private val db: PostsDb
) : PostsRepository {
    override fun getPosts(): Flow<Resource<List<Post>>> {
        // to apply single of truth principle we will always get the data from single place (room db)
        // so fetch posts from the server -> update db -> get data from db
        return flow {
            emit(Resource.Loading())
            val postsEntities = db.postsDao().getAllPosts()
            val posts = postsEntities.map { it.toPost() }
            emit(Resource.Loading(posts))
            when (val result = api.getPosts()) {
                is Resource.Success -> {
                    db.postsDao().updatePostsCache(result.data!!.map {
                        it.toPostEntity().copy(
                            isFavourite = postsEntities.find { post -> post.id == it.id }?.isFavourite
                                ?: false,
                            needToRsyncToServer = postsEntities.find { post -> post.id == it.id }?.needToRsyncToServer
                                ?: false
                        )
                    })
                    emit(Resource.Success(data = db.postsDao().getAllPosts().map { it.toPost() }))
                }

                is Resource.Error -> {
                    emit(Resource.Error(message = result.message ?: "unknown error"))
                }

                is Resource.Loading -> Unit
            }
        }
    }

    override fun getPostsComments(postId: Int): Flow<Resource<List<PostComment>>> {
        return flow {
            emit(Resource.Loading())
            when (val result = api.getPostsComments(postId)) {
                is Resource.Error -> {
                    emit(Resource.Error(message = result.message ?: "unknown error"))
                }
                is Resource.Success -> {
                    emit(Resource.Success(data = result.data?.map { it.toPostComment()}))
                }
                is Resource.Loading -> Unit
            }
        }
    }

    override fun getFavouritePosts(): Flow<Resource<List<Post>>> {
        return flow {
            emit(Resource.Loading())
            val postsEntities = db.postsDao().getFavouritePosts()
            val posts = postsEntities.map { it.toPost() }
            emit(Resource.Success(data = posts))
        }
    }

    override suspend fun getPostsToRsync(): List<Post> {
        return db.postsDao().getPostsToSync().map { it.toPost() }
    }

    override suspend fun updatePostFavouriteValue(postId: Int, isFavourite: Boolean) {
        db.postsDao().updatePostFavouriteValue(postId, isFavourite)
    }

    override suspend fun updatePostNeedToRsyncValue(postId: Int, needToRsync: Boolean) {
        db.postsDao().updatePostNeedToRsyncToServerValue(postId, needToRsync)
    }
}