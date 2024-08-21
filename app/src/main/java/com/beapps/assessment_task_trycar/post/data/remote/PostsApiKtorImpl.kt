package com.beapps.assessment_task_trycar.post.data.remote

import com.beapps.assessment_task_trycar.post.data.remote.dto.PostCommentDto
import com.beapps.assessment_task_trycar.post.data.remote.dto.PostDto
import com.beapps.assessment_task_trycar.post.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class PostsApiKtorImpl(
    private val client: HttpClient
) : PostsApi {
    override suspend fun getPosts(): Resource<List<PostDto>> {
        return try {
            val response = client.get("${PostsApi.BASE_URL}${PostsApi.POSTS_ENDPOINT}")
            when (response.status.value) {
                in 200..299 -> {
                    val postsDto = response.body<List<PostDto>>()
                    Resource.Success(data = postsDto)
                }

                else -> {
                    Resource.Error(message = "Empty Response")
                }
            }
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "Unknown Error")
        }
    }

    override suspend fun getPostsComments(postId: Int): Resource<List<PostCommentDto>> {
        return try {
            val response = client.get(PostsApi.getCommentsUrl(postId))
            when (response.status.value) {
                in 200..299 -> {
                    val postsCommentsDto = response.body<List<PostCommentDto>>()
                    Resource.Success(data = postsCommentsDto)
                }

                else -> {
                    Resource.Error(message = "Empty Response")
                }
            }
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "Unknown Error")
        }
    }
}