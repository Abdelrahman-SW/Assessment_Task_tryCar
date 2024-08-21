package com.beapps.assessment_task_trycar.post.presentation.post_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beapps.assessment_task_trycar.post.domain.models.Post
import com.beapps.assessment_task_trycar.post.domain.use_cases.PostUseCases
import com.beapps.assessment_task_trycar.post.domain.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PostsViewModel(
    private val postUseCases: PostUseCases
) : ViewModel(
) {
    var isLoading by mutableStateOf(false)
        private set

    var posts by mutableStateOf(emptyList<Post>())
        private set

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        postUseCases.getPosts().onEach { result ->
            when (result) {
                is Resource.Error -> {
                    isLoading = false
                    _eventFlow.emit(UIEvent.ShowErrorMessage(result.message ?: "Unknown error"))
                }

                is Resource.Loading -> {
                    isLoading = true
                    result.data?.let { posts ->
                        this.posts = posts
                    }
                }

                is Resource.Success -> {
                    isLoading = false
                    result.data?.let { posts ->
                        this.posts = posts
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onPostClicked(post: Post) {
        viewModelScope.launch {
            _eventFlow.emit(UIEvent.GoToPostCommentsScreen(post.id))
        }
    }

    sealed class UIEvent {
        data class ShowErrorMessage(val message: String) : UIEvent()
        data class GoToPostCommentsScreen(val postId: Int) : UIEvent()
    }
}