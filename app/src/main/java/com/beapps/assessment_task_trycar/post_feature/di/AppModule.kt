package com.beapps.assessment_task_trycar.post_feature.di

import android.content.Context
import androidx.room.Room
import com.beapps.assessment_task_trycar.post_feature.data.local.PostsDb
import com.beapps.assessment_task_trycar.post_feature.data.remote.PostsApi
import com.beapps.assessment_task_trycar.post_feature.data.remote.PostsApiKtorImpl
import com.beapps.assessment_task_trycar.post_feature.data.repository.PostsRepositoryImpl
import com.beapps.assessment_task_trycar.post_feature.domain.repository.PostsRepository
import com.beapps.assessment_task_trycar.post_feature.domain.use_cases.GetFavouritePosts
import com.beapps.assessment_task_trycar.post_feature.domain.use_cases.GetPostComments
import com.beapps.assessment_task_trycar.post_feature.domain.use_cases.GetPosts
import com.beapps.assessment_task_trycar.post_feature.domain.use_cases.PostUseCases
import com.beapps.assessment_task_trycar.post_feature.domain.use_cases.SyncFavouritePosts
import com.beapps.assessment_task_trycar.post_feature.domain.use_cases.UpdatePostFavouriteValue
import com.beapps.assessment_task_trycar.post_feature.domain.util.AppConnectivityManager
import com.beapps.assessment_task_trycar.post_feature.domain.util.AppConnectivityManagerImpl
import com.beapps.assessment_task_trycar.post_feature.presentation.post_comments.PostCommentsViewModel
import com.beapps.assessment_task_trycar.post_feature.presentation.posts_and_favourites.PostsViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single {
        PostUseCases(
            getPosts = GetPosts(repository = get()),
            getComments = GetPostComments(repository = get()),
            getFavouritePosts = GetFavouritePosts(repository = get()),
            syncFavouritePosts = SyncFavouritePosts(repository = get()),
            updatePostFavouriteValue = UpdatePostFavouriteValue(repository = get()),
        )
    }
    singleOf(::PostsRepositoryImpl).bind<PostsRepository>()
    singleOf(::PostsApiKtorImpl).bind<PostsApi>()
    singleOf(::AppConnectivityManagerImpl).bind<AppConnectivityManager>()
    viewModelOf(::PostsViewModel)
    viewModelOf(::PostCommentsViewModel)

    single {
        HttpClient(Android) {
            install(Logging)
            install(ContentNegotiation){
                json(json = Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    single {
       Room.databaseBuilder(
           get<Context>(),
           PostsDb::class.java,
           PostsDb.DATABASE_NAME
       ).build()
    }
}