package com.beapps.assessment_task_trycar.post.domain.use_cases

import com.beapps.assessment_task_trycar.post.domain.models.Post
import com.beapps.assessment_task_trycar.post.domain.repository.PostsRepository
import com.beapps.assessment_task_trycar.post.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class GetFavouritePosts (
    private val repository: PostsRepository
) {
    operator fun invoke() : Flow<Resource<Flow<List<Post>>>> {
        return repository.getFavouritePosts()
    }
}