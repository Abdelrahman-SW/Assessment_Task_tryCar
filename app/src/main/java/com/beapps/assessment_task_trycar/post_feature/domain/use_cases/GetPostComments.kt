package com.beapps.assessment_task_trycar.post_feature.domain.use_cases

import com.beapps.assessment_task_trycar.post_feature.domain.models.PostComment
import com.beapps.assessment_task_trycar.post_feature.domain.repository.PostsRepository
import com.beapps.assessment_task_trycar.post_feature.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class GetPostComments (
    private val repository: PostsRepository
) {
    operator fun invoke(postId : Int) : Flow<Resource<List<PostComment>>> {
        return repository.getPostsComments(postId)
    }
}