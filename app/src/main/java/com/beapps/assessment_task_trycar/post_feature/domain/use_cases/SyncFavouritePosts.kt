package com.beapps.assessment_task_trycar.post_feature.domain.use_cases

import com.beapps.assessment_task_trycar.post_feature.domain.repository.PostsRepository

class SyncFavouritePosts (
    private val repository: PostsRepository
) {
    suspend operator fun invoke()  {
        return repository.syncAllFavouritePosts()
    }
}