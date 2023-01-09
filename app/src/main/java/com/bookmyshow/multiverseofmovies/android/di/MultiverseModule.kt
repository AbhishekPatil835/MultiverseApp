package com.bookmyshow.multiverseofmovies.android.di

import com.bookmyshow.multiverseofmovies.android.repository.MultiverseApi
import com.bookmyshow.multiverseofmovies.android.repository.MultiverseCastCrewApi
import com.bookmyshow.multiverseofmovies.network.NetworkFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MultiverseModule {

    @Provides
    @ViewModelScoped
    fun provideMultiverseApi(
        networkFactory: NetworkFactory
    ): MultiverseApi {
        return networkFactory.getService(MultiverseApi::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideMultiverseCastCrewApi(
        networkFactory: NetworkFactory
    ): MultiverseCastCrewApi {
        return networkFactory.getService(MultiverseCastCrewApi::class.java)
    }
}