package com.beapps.assessment_task_trycar.post.domain.use_cases

import com.beapps.assessment_task_trycar.post.domain.models.Post
import com.beapps.assessment_task_trycar.post.domain.repository.PostsRepository

class SyncFavouritePosts (
    private val repository: PostsRepository
) {
    suspend operator fun invoke()  {
        return repository.syncAllFavouritePosts()
    }
}