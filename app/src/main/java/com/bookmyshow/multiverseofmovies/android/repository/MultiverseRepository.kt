package com.bookmyshow.multiverseofmovies.android.repository

import com.bookmyshow.multiverseofmovies.android.models.MultiverseResponse
import dagger.hilt.android.scopes.ViewModelScoped
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import javax.inject.Inject

@ViewModelScoped
class MultiverseRepository @Inject constructor(
    private val multiverseApi: MultiverseApi
) {

    fun getMovieDetails(
    ) : Single<Response<MultiverseResponse>> {
        return multiverseApi.getMovieDetails()
    }
}