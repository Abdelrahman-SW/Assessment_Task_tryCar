package com.beapps.assessment_task_trycar.post_feature.data.remote

import android.content.Context
import com.beapps.assessment_task_trycar.post_feature.data.remote.dto.PostCommentDto
import com.beapps.assessment_task_trycar.post_feature.data.remote.dto.PostDto
import com.beapps.assessment_task_trycar.post_feature.domain.util.AppConnectivityManager
import com.beapps.assessment_task_trycar.post_feature.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.delay

class PostsApiKtorImpl(
    private val client: HttpClient,
    val context : Context,
    val appConnectivityManager: AppConnectivityManager
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

    override suspend fun syncFavouritePost(postId: Int, isFavourite: Boolean) : Boolean{
          // check for internet connection (if no connection return false else return true after delay of 1 second)
          if (!appConnectivityManager.isNetworkAvailable()) return false
          delay(1000)
          return true
    }
}