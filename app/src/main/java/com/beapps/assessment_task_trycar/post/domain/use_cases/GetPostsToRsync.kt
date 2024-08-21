package com.beapps.assessment_task_trycar.post.domain.use_cases

import com.beapps.assessment_task_trycar.post.domain.models.Post
import com.beapps.assessment_task_trycar.post.domain.repository.PostsRepository
import com.beapps.assessment_task_trycar.post.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class GetPostsToRsync (
    private val repository: PostsRepository
) {
    suspend operator fun invoke() : List<Post> {
        return repository.getPostsToRsync()
    }
}