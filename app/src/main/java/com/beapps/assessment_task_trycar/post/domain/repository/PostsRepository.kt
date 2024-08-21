package com.beapps.assessment_task_trycar.post.domain.repository

import com.beapps.assessment_task_trycar.post.domain.models.Post
import com.beapps.assessment_task_trycar.post.domain.models.PostComment
import com.beapps.assessment_task_trycar.post.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    fun getPosts(): Flow<Resource<List<Post>>>
    fun getPostsComments(postId: Int): Flow<Resource<List<PostComment>>>
    fun getFavouritePosts(): Flow<Resource<List<Post>>>
    suspend fun getPostsToRsync(): List<Post>
    suspend fun updatePostFavouriteValue (postId: Int , isFavourite: Boolean)
    suspend fun updatePostNeedToRsyncValue (postId: Int , needToRsync: Boolean)
}