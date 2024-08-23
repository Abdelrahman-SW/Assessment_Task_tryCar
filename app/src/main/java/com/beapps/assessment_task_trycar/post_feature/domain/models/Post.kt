package com.beapps.assessment_task_trycar.post_feature.domain.models

data class Post(
    val id : Int ,
    val userId : Int,
    val title : String ,
    val body : String ,
    val isFavorite : Boolean = false
)
