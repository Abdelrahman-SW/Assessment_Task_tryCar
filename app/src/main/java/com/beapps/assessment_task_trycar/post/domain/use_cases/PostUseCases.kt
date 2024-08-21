package com.beapps.assessment_task_trycar.post.domain.use_cases

data class PostUseCases(
    val getPosts: GetPosts,
    val getComments: GetPostComments,
    val getFavouritePosts: GetFavouritePosts,
    val getPostsToRsync: GetPostsToRsync,
    val updatePostNeedRsyncValue: UpdatePostNeedRsyncValue,
    val updatePostFavouriteValue: UpdatePostFavouriteValue
)
