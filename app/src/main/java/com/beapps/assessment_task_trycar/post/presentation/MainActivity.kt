package com.beapps.assessment_task_trycar.post.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.beapps.assessment_task_trycar.post.presentation.post_comments.PostsCommentsScreen
import com.beapps.assessment_task_trycar.post.presentation.post_list.PostsListScreen
import com.beapps.assessment_task_trycar.post.presentation.post_list.PostsViewModel
import com.beapps.assessment_task_trycar.post.presentation.util.Screens
import com.beapps.assessment_task_trycar.ui.theme.Assessment_Task_tryCarTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext
import org.koin.core.context.KoinContext

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
                            val postId = it.toRoute<Screens.PostCommentsScreen>().postId
                            PostsCommentsScreen(navController = navController, postId = postId)
                        }
                    }
                }
            }
        }
    }}
