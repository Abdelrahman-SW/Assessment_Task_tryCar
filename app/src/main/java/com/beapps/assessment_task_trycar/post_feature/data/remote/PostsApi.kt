package com.beapps.assessment_task_trycar.post_feature.data.remote

import com.beapps.assessment_task_trycar.post_feature.data.remote.dto.PostCommentDto
import com.beapps.assessment_task_trycar.post_feature.data.remote.dto.PostDto
import com.beapps.assessment_task_trycar.post_feature.domain.util.Resource

interface PostsApi {
    suspend fun getPosts () : Resource<List<PostDto>>
    suspend fun getPostsComments(postId : Int) : Resource<List<PostCommentDto>>
    suspend fun syncFavouritePost(postId: Int , isFavourite : Boolean) : Boolean

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
        const val POSTS_ENDPOINT = "posts"
        fun getCommentsUrl(postId : Int) = "$BASE_URL$POSTS_ENDPOINT/$postId/comments"
    }
}