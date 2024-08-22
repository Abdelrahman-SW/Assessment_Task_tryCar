package com.beapps.assessment_task_trycar.post.data.remote

import com.beapps.assessment_task_trycar.post.data.remote.dto.PostCommentDto
import com.beapps.assessment_task_trycar.post.data.remote.dto.PostDto
import com.beapps.assessment_task_trycar.post.domain.util.Resource
import kotlinx.coroutines.flow.Flow

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