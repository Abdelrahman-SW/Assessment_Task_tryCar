package com.beapps.assessment_task_trycar.post_feature.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.beapps.assessment_task_trycar.post_feature.data.local.entities.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {
    @Query("SELECT * FROM posts")
    fun getAllPostsFlow(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts")
    suspend fun getAllPosts(): List<PostEntity>

    @Query("DELETE FROM posts")
    suspend fun deleteAllPosts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Transaction
    suspend fun updatePostsCache(posts: List<PostEntity>) {
        deleteAllPosts()
        insertPosts(posts)
    }

    @Query("Select * from posts where isFavourite = 1")
    fun getFavouritePosts() : Flow<List<PostEntity>>

    @Update
    suspend fun updatePost(post: PostEntity)

    @Query("update posts set isFavourite = :isFavourite where id = :postId")
    suspend fun updatePostFavouriteValue(postId : Int , isFavourite : Boolean)

    @Query("update posts set needToRsyncToServer = :needToRsyncToServer where id = :postId")
    suspend fun updatePostNeedToRsyncToServerValue(postId : Int , needToRsyncToServer : Boolean)

    @Query ("Select * from posts where needToRsyncToServer =1")
    suspend fun getPostsToSync() : List<PostEntity>

}