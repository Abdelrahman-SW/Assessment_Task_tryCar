package com.beapps.assessment_task_trycar.post.domain.models

data class PostComment(
    val id : Int,
    val postId : Int ,
    val name : String,
    val email : String,
    val body : String
)
