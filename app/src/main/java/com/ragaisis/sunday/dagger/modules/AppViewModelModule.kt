package com.ragaisis.sunday.dagger.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.ragaisis.sunday.dagger.AppViewModelFactory
import com.ragaisis.sunday.dagger.ViewModelKey
import com.ragaisis.sunday.viewmodels.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AppViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(aboutPageViewModel: SearchViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

}