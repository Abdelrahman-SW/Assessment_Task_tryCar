package com.beapps.assessment_task_trycar.post_feature.data.repository

import com.beapps.assessment_task_trycar.post_feature.data.local.PostsDb
import com.beapps.assessment_task_trycar.post_feature.data.local.entities.PostEntity
import com.beapps.assessment_task_trycar.post_feature.data.remote.PostsApi
import com.beapps.assessment_task_trycar.post_feature.data.remote.dto.PostDto
import com.beapps.assessment_task_trycar.post_feature.domain.util.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first

class PostsRepositoryImplTest {
    private lateinit var postsRepositoryImpl: PostsRepositoryImpl
    private lateinit var db: PostsDb
    private lateinit var api: PostsApi


    @Before
    fun setup() {
        db = mockk(relaxed = true)
        api = mockk(relaxed = true)
        postsRepositoryImpl = PostsRepositoryImpl(api, db)
    }

    @Test
    fun `getPosts When User Is Offline will Return Posts From Cache Local Database`() = runTest {
        // make api call return error as the user is offline
        coEvery { api.getPosts() } returns Resource.Error("No Internet Connection")
        val fakePostsInDb = listOf(
            PostEntity(1, 5, "title1", "body1"),
            PostEntity(2, 6, "title2", "body2"),
        )
        coEvery { db.postsDao().getAllPosts() } returns fakePostsInDb
        coEvery { db.postsDao().getAllPostsFlow() } returns flowOf(fakePostsInDb)

        val postsFlow = postsRepositoryImpl.getPosts()
        val postsFlowList = postsFlow.toList()
        // expect loading , loading with cache data , error
        assert(postsFlowList[0] is Resource.Loading)
        assert(postsFlowList[1] is Resource.Loading)
        assert(postsFlowList[2] is Resource.Error)
        // seconding loading must have the value on the cache
        assertThat(postsFlowList[1].data?.first()?.size ?: 0).isEqualTo(2)
        assertThat(postsFlowList[2].message).isEqualTo("No Internet Connection")
    }



    @After
    fun tearDown() {
        clearAllMocks()
    }

}