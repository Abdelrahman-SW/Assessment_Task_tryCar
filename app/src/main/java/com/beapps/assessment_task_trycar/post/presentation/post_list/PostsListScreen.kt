package com.beapps.assessment_task_trycar.post.presentation.post_list

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Feed
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.beapps.assessment_task_trycar.post.presentation.post_list.components.PostItem
import com.beapps.assessment_task_trycar.post.presentation.util.BottomNavigationItems
import com.beapps.assessment_task_trycar.post.presentation.util.Screens
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

        if (true) { //check for connection if exits sync data
            viewModel.syncFavouritePosts()
        }

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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                title = {
                    Text(
                        text = if (selectedBottomBarItemIndex == 0) "Posts" else "Favourites",
                        fontFamily = poppinsFontFamily,
                    )
                },
            )
        },
        bottomBar = {
            NavigationBar {
                bottomBarNavigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedBottomBarItemIndex == index,
                        onClick = { selectedBottomBarItemIndex = index },
                        label = {
                            Text(
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

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {


            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
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

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

        }
    }
}