package com.beapps.assessment_task_trycar.post.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beapps.assessment_task_trycar.post.domain.models.Post

@Entity(tableName = "Posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val needToRsyncToServer : Boolean = false,
    val isFavourite : Boolean = false
) {
    fun toPost() : Post {
        return Post(
            id = id,
            userId = userId,
            title = title,
            body = body,
            isFavorite = isFavourite
        )
    }
}
