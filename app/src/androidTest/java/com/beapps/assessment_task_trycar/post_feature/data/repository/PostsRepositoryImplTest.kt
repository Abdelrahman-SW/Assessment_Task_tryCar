package com.beapps.assessment_task_trycar.post_feature.data.repository

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.beapps.assessment_task_trycar.post_feature.data.local.PostsDb
import com.beapps.assessment_task_trycar.post_feature.data.local.entities.PostEntity
import com.beapps.assessment_task_trycar.post_feature.data.remote.PostsApi
import com.beapps.assessment_task_trycar.post_feature.data.remote.dto.PostDto
import com.beapps.assessment_task_trycar.post_feature.domain.util.Resource
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class PostsRepositoryImplAndroidTest {
    private lateinit var postsRepositoryImpl: PostsRepositoryImpl
    private lateinit var testDb: PostsDb
    private lateinit var api: PostsApi

    @Before
    fun setup() {
        api = mockk(relaxed = true)
        testDb = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            PostsDb::class.java
        ).build()
        postsRepositoryImpl = PostsRepositoryImpl(api, testDb)
    }



    @Test
    fun getUpdatedPostsFromApi_UpdatesTheCacheOutdatedData() = runTest {
        val outdatedPostsInDb = listOf(
            PostEntity(1, 5, "title1", "body1"),
            PostEntity(2, 6, "title2", "body2"),
        )
        testDb.postsDao().insertPosts(outdatedPostsInDb)

        val fakeNewPostsFromApi = listOf(
            PostDto(1, 5, "new title1", "new body1"),
            PostDto(2, 6, "title2", "body2"),
        )

        coEvery { api.getPosts() } returns Resource.Success(data =  fakeNewPostsFromApi)
        postsRepositoryImpl.getPosts().toList() // ensure all emissions are received
        assertThat(testDb.postsDao().getAllPosts()[0].title).isEqualTo("new title1")
        assertThat(testDb.postsDao().getAllPosts()[0].body).isEqualTo("new body1")
    }


    @Test
    fun getFavouritePosts_showCorrectItems() = runTest {
        val postsInDb = listOf(
            PostEntity(1, 5, "title1", "body1" , isFavourite = true),
            PostEntity(2, 6, "title2", "body2", isFavourite = false),
            PostEntity(2, 6, "title3", "body2", isFavourite = false),
            PostEntity(2, 6, "title4", "body2", isFavourite = true),
        )
        testDb.postsDao().insertPosts(postsInDb)


        // loading - success
        val result = postsRepositoryImpl.getFavouritePosts().toList()
        val successResult = result[1]
        assertThat(successResult.data?.first()?.size ?: 0).isEqualTo(postsInDb.filter { it.isFavourite }.size)
        assertThat(successResult.data?.first()?.get(0)?.title ?: "").isEqualTo("title1")
        assertThat(successResult.data?.first()?.get(1)?.title ?: "").isEqualTo("title4")
    }


    @Test
    fun syncFavouritePostsFailed_MakePostNeedToRsyncLater() = runTest{
        val postsInDb = listOf(
            PostEntity(1, 5, "title1", "body1" , isFavourite = true , needToRsyncToServer = false),
            PostEntity(2, 6, "title2", "body2", isFavourite = false, needToRsyncToServer = false),
            PostEntity(3, 6, "title3", "body2", isFavourite = true, needToRsyncToServer = false),
            PostEntity(4, 6, "title4", "body2", isFavourite = true, needToRsyncToServer = false),
        )
        testDb.postsDao().insertPosts(postsInDb)

        /// mock sync favourite to always failed (eg : user offline)
        coEvery { api.syncFavouritePost(any() , any()) } returns false
        // user makes post 2 favourite
        postsRepositoryImpl.updatePostFavouriteValue(2 , true)
        // needToRsyncToServer should now be true for post 2
        assertThat(testDb.postsDao().getAllPosts()[1].needToRsyncToServer).isTrue()
    }

    @Test
    fun syncFavouritePostsThatSuccess_MakePostsNoLongerNeedToRsync() = runTest{
        val postsInDb = listOf(
            PostEntity(1, 5, "title1", "body1" , isFavourite = true , needToRsyncToServer = true),
            PostEntity(2, 6, "title2", "body2", isFavourite = false, needToRsyncToServer = false),
            PostEntity(3, 6, "title3", "body2", isFavourite = true, needToRsyncToServer = false),
            PostEntity(4, 6, "title4", "body2", isFavourite = true, needToRsyncToServer = true),
        )
        testDb.postsDao().insertPosts(postsInDb)

        /// mock sync favourite to always done (eg : user online)
        coEvery { api.syncFavouritePost(any() , any()) } returns true
        postsRepositoryImpl.syncAllFavouritePosts()
        // needToRsyncToServer should now be true for post 2
        assertThat(testDb.postsDao().getAllPosts()[0].needToRsyncToServer).isFalse()
        assertThat(testDb.postsDao().getAllPosts()[3].needToRsyncToServer).isFalse()
    }


    @After
    fun tearDown() {
        clearAllMocks()
    }
}