package com.ragaisis.sunday.dagger

import com.ragaisis.sunday.MainActivity
import com.ragaisis.sunday.MyApplication
import com.ragaisis.sunday.dagger.modules.AppModule
import com.ragaisis.sunday.dagger.modules.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface MainComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: MyApplication)

}