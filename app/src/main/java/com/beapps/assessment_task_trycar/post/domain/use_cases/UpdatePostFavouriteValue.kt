package com.beapps.assessment_task_trycar.post.domain.use_cases

import com.beapps.assessment_task_trycar.post.domain.models.Post
import com.beapps.assessment_task_trycar.post.domain.repository.PostsRepository
import com.beapps.assessment_task_trycar.post.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class UpdatePostFavouriteValue (
    private val repository: PostsRepository
) {
    suspend operator fun invoke(postId: Int, isFavourite: Boolean) {
        return repository.updatePostFavouriteValue(postId, isFavourite)
    }
}