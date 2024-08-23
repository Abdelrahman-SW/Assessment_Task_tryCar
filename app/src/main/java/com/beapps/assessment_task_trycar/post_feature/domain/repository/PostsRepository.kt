package com.beapps.assessment_task_trycar.post_feature.domain.repository

import com.beapps.assessment_task_trycar.post_feature.domain.models.Post
import com.beapps.assessment_task_trycar.post_feature.domain.models.PostComment
import com.beapps.assessment_task_trycar.post_feature.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    fun getPosts(): Flow<Resource<Flow<List<Post>>>>
    fun getPostsComments(postId: Int): Flow<Resource<List<PostComment>>>
    fun getFavouritePosts(): Flow<Resource<Flow<List<Post>>>>
    suspend fun updatePostFavouriteValue (postId: Int , isFavourite: Boolean)
    suspend fun updatePostNeedToRsyncValue (postId: Int , needToRsync: Boolean)
    suspend fun syncAllFavouritePosts()
}