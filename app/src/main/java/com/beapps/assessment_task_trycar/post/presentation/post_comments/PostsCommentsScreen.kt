package com.beapps.assessment_task_trycar.post.presentation.post_comments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.beapps.assessment_task_trycar.ui.theme.poppinsFontFamily

@Composable
fun PostsCommentsScreen(
    modifier: Modifier = Modifier,
    postId: Int,
    navController: NavController,
) {
    Box(modifier = modifier.fillMaxSize() , contentAlignment = Alignment.Center) {
        Text(
            text = "Post Id = $postId",
            fontFamily = poppinsFontFamily
        )
    }
}