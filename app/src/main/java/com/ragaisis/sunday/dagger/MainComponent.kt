package com.ragaisis.sunday.dagger

import com.ragaisis.sunday.MyApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface MainComponent {

    fun inject(activity: MyApplication)

}