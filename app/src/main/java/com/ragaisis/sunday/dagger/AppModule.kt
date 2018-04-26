package com.ragaisis.sunday.dagger

import com.ragaisis.sunday.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: MyApplication) {

    @Provides
    @Singleton
    internal fun provideApplication(): MyApplication {
        return application
    }

}