package com.bookmyshow.multiverseofmovies.android.repository

import com.bookmyshow.multiverseofmovies.android.models.MultiverseCastResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET

const val GET_MOVIE_CAST_DETAILS = "/3/movie/453395/credits?api_key=0799fb14a63d4ffe0ff4496b6bd30cfe&language=en-US/"
interface MultiverseCastCrewApi {

    @GET(GET_MOVIE_CAST_DETAILS)
    fun getCastDetails(): Single<Response<MultiverseCastResponse>>
}