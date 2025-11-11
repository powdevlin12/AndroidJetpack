package com.dattran.unitconverter.mvvm.di

import android.util.Log
import com.dattran.unitconverter.mvvm.repositories.MainLog
import com.dattran.unitconverter.mvvm.repositories.MainLogImpl
import com.dattran.unitconverter.mvvm.repositories.Store
import com.dattran.unitconverter.mvvm.repositories.StoreImpl
import com.dattran.unitconverter.mvvm.repositories.StoreImpl2
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    @Singleton
    abstract fun bindMainLog(mainLog: MainLogImpl): MainLog

    @Binds
    @Singleton
    abstract fun bindStore(storeImpl: StoreImpl2): Store
}