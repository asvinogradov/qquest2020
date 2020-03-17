package com.htccs.android.neneyolka.base

import android.app.Application
import com.htccs.android.neneyolka.module.firebaseModule
import com.htccs.android.neneyolka.module.interactorsModule
import com.htccs.android.neneyolka.module.utilsModule
import com.htccs.android.neneyolka.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NeNeYolkaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NeNeYolkaApplication)
            modules(listOf(firebaseModule, viewModelModule, utilsModule, interactorsModule))
        }
    }
}