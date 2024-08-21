package com.beapps.assessment_task_trycar.post.data.remote.dto

import com.beapps.assessment_task_trycar.post.data.local.entities.PostEntity
import com.beapps.assessment_task_trycar.post.domain.models.Post
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
) {
    fun toPost(): Post {
        return Post(
            id = this.id,
            userId = userId,
            title = this.title,
            body = this.body ,
            isFavorite = false
        )
    }

    fun toPostEntity(): PostEntity {
        return PostEntity(
            id = this.id,
            userId = this.userId,
            title = this.title,
            body = this.body
        )
    }
}
