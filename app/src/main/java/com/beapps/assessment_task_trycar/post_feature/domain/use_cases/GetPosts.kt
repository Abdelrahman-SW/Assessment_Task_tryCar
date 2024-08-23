package com.beapps.assessment_task_trycar.post_feature.domain.use_cases

import com.beapps.assessment_task_trycar.post_feature.domain.models.Post
import com.beapps.assessment_task_trycar.post_feature.domain.repository.PostsRepository
import com.beapps.assessment_task_trycar.post_feature.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class GetPosts (
    private val repository: PostsRepository
) {
    operator fun invoke() : Flow<Resource<Flow<List<Post>>>> {
        return repository.getPosts()
    }
}