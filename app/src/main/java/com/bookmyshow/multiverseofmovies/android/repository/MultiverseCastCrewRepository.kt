package com.bookmyshow.multiverseofmovies.android.repository

import com.bookmyshow.multiverseofmovies.android.models.MultiverseCastResponse
import dagger.hilt.android.scopes.ViewModelScoped
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import javax.inject.Inject

@ViewModelScoped
class MultiverseCastCrewRepository @Inject constructor(
    private val multiverseCastCrewApi: MultiverseCastCrewApi
) {

    fun getMovieCastCrewDetails(
    ) : Single<Response<MultiverseCastResponse>> {
        return multiverseCastCrewApi.getCastDetails()
    }
}