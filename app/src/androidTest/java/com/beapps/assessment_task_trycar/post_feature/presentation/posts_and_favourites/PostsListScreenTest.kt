package com.beapps.assessment_task_trycar.post_feature.presentation.posts_and_favourites

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.beapps.assessment_task_trycar.post_feature.domain.models.Post
import com.beapps.assessment_task_trycar.post_feature.domain.util.NetworkState
import com.beapps.assessment_task_trycar.ui.theme.Assessment_Task_tryCarTheme
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test

class PostsListScreenTest {
    @get:Rule
    val composeRule = createComposeRule()
    private lateinit var postsViewModel: PostsViewModel


    @Test
    fun showsLoadingIndicatorWhenLoading() = runTest {
        // Given a view model with isLoading set to true
        // Then the CircularProgressIndicator should be visible
        composeRule.setContent {
            Assessment_Task_tryCarTheme {
                postsViewModel = mockViewModel()
                PostsListScreen(navController = mockk(relaxed = true), viewModel = postsViewModel)
            }
        }
        composeRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }


    @Test
    fun testPostsListScreenDisplaysPosts() {
        // Given some posts in the mock ViewModel
        composeRule.setContent {
            Assessment_Task_tryCarTheme {
                postsViewModel = mockViewModel(_posts =
                listOf(
                    Post(id = 1, userId = 5, title = "Title 1", body = "Body 1"),
                    Post(id = 2, userId = 5, title = "Title 2", body = "Body 2")
                ))
                PostsListScreen(navController = mockk(relaxed = true), viewModel = postsViewModel)
            }
        }
        // Then verify the posts are displayed
        composeRule.onNodeWithText("Title 1").assertIsDisplayed()
        composeRule.onNodeWithText("Body 1").assertIsDisplayed()
        composeRule.onNodeWithText("Title 2").assertIsDisplayed()
        composeRule.onNodeWithText("Body 2").assertIsDisplayed()
    }


    @After
    fun tearDown() {
        clearAllMocks()
    }

    fun mockViewModel(_posts: List<Post> = emptyList()): PostsViewModel {
        val mockEventFlow = MutableSharedFlow<PostsViewModel.UIEvent>()
        return mockk {
            every { isLoading } returns _posts.isEmpty()
            every { posts } returns _posts
            every { favouritePosts } returns listOf()
            every { networkState } returns NetworkState.CONNECTED
            every { eventFlow } returns mockEventFlow
            every { onPostClicked(any()) } just Runs
        }
    }

}