package com.beapps.assessment_task_trycar.post_feature.presentation.post_comments

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.beapps.assessment_task_trycar.post_feature.domain.util.NetworkState
import com.beapps.assessment_task_trycar.post_feature.presentation.post_comments.components.PostCommentItem
import com.beapps.assessment_task_trycar.ui.theme.mainComponentColor
import com.beapps.assessment_task_trycar.ui.theme.poppinsFontFamily
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsCommentsScreen(
    postId: Int,
    isPostFavourite: Boolean,
    navController: NavController,
    viewModel: PostCommentsViewModel = koinViewModel()
) {
    val isLoading = viewModel.isLoading
    val postComments = viewModel.postComments
    val context = LocalContext.current
    val networkState = viewModel.networkState
    var isPostFavouriteState by rememberSaveable {
        mutableStateOf(isPostFavourite)
    }

    LaunchedEffect(key1 = true) {
        viewModel.assignPostID(postId)
        viewModel.eventFlow.collect { event ->
            when (event) {
                is PostCommentsViewModel.UIEvent.ShowErrorMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                is PostCommentsViewModel.UIEvent.ShowInfoMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = mainComponentColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
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
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable {
                                if (isLoading) return@clickable
                                isPostFavouriteState = !isPostFavouriteState
                                viewModel.onFavouriteBtnClicked(isPostFavouriteState)
                            },
                        imageVector = if (isPostFavouriteState) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favourite",
                        tint = Color.Red
                    )
                }
            )
        }

    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (networkState == NetworkState.LOST) {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        text = "Connection Lost ! - Please Check Your Internet Connection",
                        fontFamily = poppinsFontFamily,
                        textAlign = TextAlign.Center
                    )
                }

                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                ) {
                    items(postComments, key = { it.id }) { postComment ->
                        PostCommentItem(postComment = postComment)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

            }
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )

            }
        }
    }
}