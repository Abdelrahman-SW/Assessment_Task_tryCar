package com.beapps.assessment_task_trycar.post.domain.models

import com.beapps.assessment_task_trycar.post.data.local.entities.PostEntity

data class Post(
    val id : Int ,
    val userId : Int,
    val title : String ,
    val body : String ,
    val isFavorite : Boolean = false
)
