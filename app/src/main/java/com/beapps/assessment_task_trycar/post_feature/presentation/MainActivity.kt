package com.beapps.assessment_task_trycar.post_feature.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.beapps.assessment_task_trycar.post_feature.presentation.post_comments.PostsCommentsScreen
import com.beapps.assessment_task_trycar.post_feature.presentation.posts_and_favourites.PostsListScreen
import com.beapps.assessment_task_trycar.post_feature.presentation.util.Screens
import com.beapps.assessment_task_trycar.ui.theme.Assessment_Task_tryCarTheme
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assessment_Task_tryCarTheme {
                KoinContext {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screens.PostsScreen
                    ) {
                        composable<Screens.PostsScreen> {
                            PostsListScreen(navController = navController)
                        }
                        composable<Screens.PostCommentsScreen> {
                            val postCommentScreenArgs = it.toRoute<Screens.PostCommentsScreen>()
                            val postId = postCommentScreenArgs.postId
                            val isPostFavourite = postCommentScreenArgs.isPostFavourite
                            PostsCommentsScreen(postId = postId, isPostFavourite = isPostFavourite ,  navController = navController)
                        }
                    }
                }
            }
        }
    }}
