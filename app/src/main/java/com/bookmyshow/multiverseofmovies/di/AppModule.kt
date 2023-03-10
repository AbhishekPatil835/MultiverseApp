package com.bookmyshow.multiverseofmovies.di

import com.bookmyshow.multiverseofmovies.network.OkHttpClientFactory
import com.bookmyshow.multiverseofmovies.network.OkHttpClientFactoryDependencyImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesOkHttpClientFactoryDependency(
    ): OkHttpClientFactory.Dependency {
        return OkHttpClientFactoryDependencyImpl()
    }


}