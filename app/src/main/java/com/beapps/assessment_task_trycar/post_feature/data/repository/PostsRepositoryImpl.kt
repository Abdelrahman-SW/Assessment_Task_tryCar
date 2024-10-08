package com.beapps.assessment_task_trycar.post_feature.data.repository

import com.beapps.assessment_task_trycar.post_feature.data.local.PostsDb
import com.beapps.assessment_task_trycar.post_feature.data.remote.PostsApi
import com.beapps.assessment_task_trycar.post_feature.domain.models.Post
import com.beapps.assessment_task_trycar.post_feature.domain.models.PostComment
import com.beapps.assessment_task_trycar.post_feature.domain.repository.PostsRepository
import com.beapps.assessment_task_trycar.post_feature.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PostsRepositoryImpl(
    private val api: PostsApi,
    private val db: PostsDb
) : PostsRepository {
    override fun getPosts(): Flow<Resource<Flow<List<Post>>>> {
        // to apply single of truth principle we will always get the data from single place (room db)
        // so fetch posts from the server -> update db -> get updated data from db
        return flow {
            emit(Resource.Loading())
            val postsEntitiesFlow = db.postsDao().getAllPostsFlow()
            val posts: Flow<List<Post>> = postsEntitiesFlow.map {
                it.map { postEntity -> postEntity.toPost() }
            }
            emit(Resource.Loading(posts))
            when (val result = api.getPosts()) {
                is Resource.Success -> {
                    val postsEntities = db.postsDao().getAllPosts()
                    db.postsDao().updatePostsCache(result.data!!.map {
                        it.toPostEntity().copy(
                            isFavourite = postsEntities.find { post -> post.id == it.id }?.isFavourite
                                ?: false,
                            needToRsyncToServer = postsEntities.find { post -> post.id == it.id }?.needToRsyncToServer
                                ?: false
                        )
                    })
                    emit(Resource.Success(data = db.postsDao().getAllPostsFlow().map {
                        it.map { postEntity -> postEntity.toPost() }
                    }))
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
                    emit(Resource.Success(data = result.data?.map { it.toPostComment() }))
                }

                is Resource.Loading -> Unit
            }
        }
    }

    override fun getFavouritePosts(): Flow<Resource<Flow<List<Post>>>> {
        return flow {
            emit(Resource.Loading())
            val postsEntities = db.postsDao().getFavouritePosts()
            val posts: Flow<List<Post>> = postsEntities.map {
                it.map { postEntity -> postEntity.toPost() }
            }
            emit(Resource.Success(data = posts))
        }
    }

    override suspend fun updatePostFavouriteValue(postId: Int, isFavourite: Boolean) {
        db.postsDao().updatePostFavouriteValue(postId, isFavourite)
        // simulate api call to sync the updated favourite value of this post
        val isSyncedSuccessfully = api.syncFavouritePost(postId, isFavourite)
        db.postsDao().updatePostNeedToRsyncToServerValue(postId, needToRsyncToServer = !isSyncedSuccessfully)
    }


    override suspend fun syncAllFavouritePosts() {
        val postsToSync = getPostsToRsync()
        if (postsToSync.isEmpty()) return // no posts need to rsync
        postsToSync.forEach { post ->
            val isSyncedSuccessfully = api.syncFavouritePost(post.id, post.isFavorite)
            db.postsDao().updatePostNeedToRsyncToServerValue(
                post.id,
                needToRsyncToServer = !isSyncedSuccessfully
            )
        }
    }

    private suspend fun getPostsToRsync(): List<Post> {
        return db.postsDao().getPostsToSync().map { it.toPost() }
    }
}