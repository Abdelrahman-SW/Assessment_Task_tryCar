package com.beapps.assessment_task_trycar.post.presentation.post_list

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.beapps.assessment_task_trycar.post.presentation.post_list.components.PostItem
import com.beapps.assessment_task_trycar.post.presentation.util.Screens
import org.koin.androidx.compose.koinViewModel

@Composable
fun PostsListScreen(
    modifier: Modifier = Modifier,
    viewModel: PostsViewModel = koinViewModel(),
    navController: NavController
) {
    val isLoading = viewModel.isLoading
    val posts = viewModel.posts
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when(event) {
                is PostsViewModel.UIEvent.ShowErrorMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }

                is PostsViewModel.UIEvent.GoToPostCommentsScreen -> {
                    navController.navigate(Screens.PostCommentsScreen(event.postId))
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (isLoading) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(32.dp))
        }
        LazyColumn {
            items(posts) {
                PostItem(post = it , onPostClicked = viewModel::onPostClicked)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}