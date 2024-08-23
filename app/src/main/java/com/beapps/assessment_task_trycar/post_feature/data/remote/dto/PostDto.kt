package com.beapps.assessment_task_trycar.post_feature.data.remote.dto

import com.beapps.assessment_task_trycar.post_feature.data.local.entities.PostEntity
import com.beapps.assessment_task_trycar.post_feature.domain.models.Post
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
) {
    fun toPostEntity(): PostEntity {
        return PostEntity(
            id = this.id,
            userId = this.userId,
            title = this.title,
            body = this.body
        )
    }
}
