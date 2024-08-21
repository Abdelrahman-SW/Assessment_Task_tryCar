package com.beapps.assessment_task_trycar.post.data.remote.dto

import com.beapps.assessment_task_trycar.post.domain.models.PostComment
import kotlinx.serialization.Serializable

@Serializable

data class PostCommentDto(
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
) {
    fun toPostComment() = PostComment(
        id = id,
        postId = postId,
        name = name,
        email = email,
        body = body
    )
}
