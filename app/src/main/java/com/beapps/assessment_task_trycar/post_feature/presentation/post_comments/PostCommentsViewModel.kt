package com.beapps.assessment_task_trycar.post_feature.presentation.post_comments

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beapps.assessment_task_trycar.post_feature.domain.models.PostComment
import com.beapps.assessment_task_trycar.post_feature.domain.use_cases.PostUseCases
import com.beapps.assessment_task_trycar.post_feature.domain.util.AppConnectivityManager
import com.beapps.assessment_task_trycar.post_feature.domain.util.NetworkState
import com.beapps.assessment_task_trycar.post_feature.domain.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PostCommentsViewModel(
    private val postUseCases: PostUseCases,
    private val connectivityManager: AppConnectivityManager
) : ViewModel() {

    var networkState by mutableStateOf(NetworkState.CONNECTED)
        private set


    var isLoading by mutableStateOf(false)
        private set

    var postComments by mutableStateOf(emptyList<PostComment>())
        private set

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentPostId : Int = -1

    private var loadCommentsJob : Job? = null

    fun assignPostID (postId: Int){
        currentPostId = postId
        connectivityManager.getNetworkState().onEach { state ->
            Log.d("ab_do", "state = ${state.name}")
            networkState = state
            if (state == NetworkState.CONNECTED) {
                loadPostComments()
            }
        }.launchIn(viewModelScope)
    }


    private fun loadPostComments() {
        loadCommentsJob?.cancel()
        loadCommentsJob = postUseCases.getComments(postId = currentPostId).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    isLoading = false
                    _eventFlow.emit(UIEvent.ShowErrorMessage(result.message ?: "Unknown error"))
                }

                is Resource.Loading -> {
                    isLoading = true
                }

                is Resource.Success -> {
                    isLoading = false
                    result.data?.let { postComments ->
                        this.postComments = postComments
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onFavouriteBtnClicked(isPostFavourite: Boolean) {
        viewModelScope.launch {
            isLoading = true
            postUseCases.updatePostFavouriteValue(currentPostId, isPostFavourite)
            isLoading = false
            _eventFlow.emit(UIEvent.ShowInfoMessage(if (isPostFavourite) "Added to favourites" else "Removed from favourites"))
        }
    }

    sealed class UIEvent {
        data class ShowErrorMessage(val message: String) : UIEvent()
        data class ShowInfoMessage(val message: String) : UIEvent()
    }
}