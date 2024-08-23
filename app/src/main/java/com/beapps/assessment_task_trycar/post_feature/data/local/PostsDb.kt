package com.beapps.assessment_task_trycar.post_feature.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.beapps.assessment_task_trycar.post_feature.data.local.entities.PostEntity

@Database(entities = [PostEntity::class] , version = 1)
abstract class PostsDb : RoomDatabase() {
    abstract fun postsDao(): PostsDao

    companion object {
        const val DATABASE_NAME: String = "posts_db"
    }
}