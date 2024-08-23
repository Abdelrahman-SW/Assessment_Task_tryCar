package com.beapps.assessment_task_trycar.post_feature.domain.use_cases

import com.beapps.assessment_task_trycar.post_feature.domain.repository.PostsRepository

class UpdatePostNeedRsyncValue (
    private val repository: PostsRepository
) {
    suspend operator fun invoke(postId: Int, needRsyncValue: Boolean) {
        return repository.updatePostNeedToRsyncValue(postId, needRsyncValue)
    }
}