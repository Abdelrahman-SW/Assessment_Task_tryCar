package com.beapps.assessment_task_trycar.post.presentation.post_comments

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.beapps.assessment_task_trycar.post.presentation.post_comments.components.PostCommentItem
import com.beapps.assessment_task_trycar.post.presentation.post_list.PostsViewModel
import com.beapps.assessment_task_trycar.post.presentation.post_list.components.PostItem
import com.beapps.assessment_task_trycar.post.presentation.util.Screens
import com.beapps.assessment_task_trycar.ui.theme.poppinsFontFamily
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsCommentsScreen(
    modifier: Modifier = Modifier,
    postId: Int,
    navController: NavController,
    viewModel: PostCommentsViewModel = koinViewModel()
) {
    val isLoading = viewModel.isLoading
    val postComments = viewModel.postComments
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.loadPostComments(postId)
        viewModel.eventFlow.collect { event ->
            when (event) {
                is PostCommentsViewModel.UIEvent.ShowErrorMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Comments",
                        fontFamily = poppinsFontFamily
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                },
                actions = {
                    Icon(
                        modifier = Modifier.padding(end = 16.dp).clickable {
                            viewModel.onFavouriteClicked()
                        },
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favourite",
                        tint = Color.Red
                    )
                }
            )
        }

    ) { padding ->

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.padding(padding)
            ) {
                items(postComments, key = { it.id }) { postComment ->
                    PostCommentItem(postComment = postComment)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}