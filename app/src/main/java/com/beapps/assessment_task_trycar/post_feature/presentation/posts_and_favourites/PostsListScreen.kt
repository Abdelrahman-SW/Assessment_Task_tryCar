package com.beapps.assessment_task_trycar.post_feature.presentation.posts_and_favourites

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.beapps.assessment_task_trycar.post_feature.domain.util.NetworkState
import com.beapps.assessment_task_trycar.post_feature.presentation.posts_and_favourites.components.PostItem
import com.beapps.assessment_task_trycar.post_feature.presentation.util.BottomNavigationItems
import com.beapps.assessment_task_trycar.post_feature.presentation.util.Screens
import com.beapps.assessment_task_trycar.ui.theme.lightBlue100
import com.beapps.assessment_task_trycar.ui.theme.mainComponentColor
import com.beapps.assessment_task_trycar.ui.theme.poppinsFontFamily
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsListScreen(
    modifier: Modifier = Modifier,
    viewModel: PostsViewModel = koinViewModel(),
    navController: NavController
) {
    val isLoading = viewModel.isLoading
    val posts = viewModel.posts
    val favouritePosts = viewModel.favouritePosts
    val networkState = viewModel.networkState
    val context = LocalContext.current

    val bottomBarNavigationItems = rememberSaveable {
        listOf(
            BottomNavigationItems(
                "Posts",
                Icons.Outlined.Search,
                Icons.Filled.Search,
            ), BottomNavigationItems(
                "Favourites",
                Icons.Filled.Favorite,
                Icons.Filled.FavoriteBorder,
                tint = Color.Red
            )
        )
    }

    bottomBarNavigationItems[1].badgeCount = favouritePosts.size

    var selectedBottomBarItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is PostsViewModel.UIEvent.ShowErrorMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }

                is PostsViewModel.UIEvent.GoToPostCommentsScreen -> {
                    navController.navigate(
                        Screens.PostCommentsScreen(
                            event.postId,
                            event.isPostFavourite
                        )
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = mainComponentColor),
                title = {
                    Text(
                        text = if (selectedBottomBarItemIndex == 0) "Posts" else "Favourites",
                        fontFamily = poppinsFontFamily,
                        color = Color.White
                    )
                },
            )
        },
        bottomBar = {
            NavigationBar (
                containerColor = mainComponentColor,
                contentColor = Color.White
            ) {
                bottomBarNavigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(indicatorColor = lightBlue100),
                        selected = selectedBottomBarItemIndex == index,
                        onClick = { selectedBottomBarItemIndex = index },
                        label = {
                            Text(
                                color = Color.White,
                                text = item.label,
                                fontFamily = poppinsFontFamily,
                                fontWeight = if (selectedBottomBarItemIndex == index) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        icon = {
                            BadgedBox(badge = {
                                item.badgeCount?.let {
                                    Badge {
                                        Text(text = item.badgeCount.toString())
                                    }
                                }
                            }) {
                                Icon(
                                    imageVector = if (selectedBottomBarItemIndex == index) item.selectedIcon else item.unSelectedIcon,
                                    contentDescription = item.label,
                                )
                            }
                        }
                    )
                }
            }
        }

    ) { padding ->


        Box(modifier = modifier.fillMaxSize().padding(padding)) {

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (networkState == NetworkState.LOST) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Network Lost ! - Please Check Your Internet Connection.",
                        fontFamily = poppinsFontFamily,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                LazyColumn {
                    val postsToShow = if (selectedBottomBarItemIndex == 0) posts else favouritePosts
                    if (selectedBottomBarItemIndex == 0) {
                        item {
                            TextField(
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                value = "",
                                onValueChange = {},
                                shape = RoundedCornerShape(16.dp),
                                placeholder = {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = "What is on your mind , Abdelrahman ?",
                                        fontFamily = poppinsFontFamily,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    items(postsToShow, key = { it.id }) {
                        PostItem(post = it, onPostClicked = viewModel::onPostClicked)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }


            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center).semantics {
                    contentDescription = "loading_indicator"
                }.testTag("loading_indicator"))
            }
        }
    }
}