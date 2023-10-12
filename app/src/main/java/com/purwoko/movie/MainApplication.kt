package com.purwoko.movie

import android.app.Application
import com.chad.library.adapter.base.module.LoadMoreModuleConfig
import com.purwoko.movie.utils.CustomLoadMoreView
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LoadMoreModuleConfig.defLoadMoreView = CustomLoadMoreView()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}