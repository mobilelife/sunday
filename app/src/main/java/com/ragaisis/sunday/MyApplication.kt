package com.ragaisis.sunday

import android.app.Application
import com.ragaisis.sunday.dagger.modules.AppModule
import com.ragaisis.sunday.dagger.DaggerMainComponent
import com.ragaisis.sunday.dagger.MainComponent


class MyApplication : Application() {

    lateinit var mainComponent: MainComponent
        private set

    override fun onCreate() {
        super.onCreate()
        mainComponent = DaggerMainComponent.builder()
                .appModule(AppModule(this))
                .build()
        mainComponent.inject(this)
    }
}
