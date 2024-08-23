package com.beapps.assessment_task_trycar

import android.app.Application
import com.beapps.assessment_task_trycar.post_feature.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}