package com.dattran.unitconverter.mvvm.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    // Bind dẫn từ Interface -> Implementation thì dùng @Binds trong abstract class, của chúng ta (k phải của thư viện)
    // Provides nó làm đc nhiều hơn, nó quy định hẳn cách cấp phát luôn, hay đc dùng với class mà ta k sở hữu (của thư viện)
    @Provides
    @Singleton
    fun provideSharedPreferencesName(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }
}