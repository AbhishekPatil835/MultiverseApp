package com.bookmyshow.multiverseofmovies.network.di

import android.content.Context
import com.bookmyshow.multiverseofmovies.network.NetworkFactory
import com.bookmyshow.multiverseofmovies.network.NetworkFactoryImpl
import com.bookmyshow.multiverseofmovies.network.OkHttpClientFactory
import com.bookmyshow.multiverseofmovies.network.RetrofitFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkFactory(retrofitFactory: RetrofitFactory): NetworkFactory {
        return NetworkFactoryImpl(retrofitFactory)
    }

    @Provides
    @Singleton
    fun providesRetrofitFactory(
        okHttpClientFactory: OkHttpClientFactory
    ): RetrofitFactory {
        return RetrofitFactory(okHttpClientFactory)
    }

    @Provides
    @Singleton
    fun providesOkHttpClientFactory(
        @ApplicationContext context: Context,
        dependency: OkHttpClientFactory.Dependency
    ) : OkHttpClientFactory {
        return OkHttpClientFactory(context, dependency)
    }
}