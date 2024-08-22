package com.beapps.assessment_task_trycar.post.presentation.util

import kotlinx.serialization.Serializable


sealed class Screens {
    @Serializable
    data object PostsScreen : Screens()

    @Serializable
    data class PostCommentsScreen(val postId: Int , val isPostFavourite : Boolean) : Screens()
}