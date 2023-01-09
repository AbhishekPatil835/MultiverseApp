package com.bookmyshow.multiverseofmovies.android.repository

import com.bookmyshow.multiverseofmovies.android.models.MultiverseResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET

const val GET_MOVIE_DETAILS = "3/movie/453395?api_key=0799fb14a63d4ffe0ff4496b6bd30cfe&language=en-US&page=1/"
interface MultiverseApi {

    @GET(GET_MOVIE_DETAILS)
    fun getMovieDetails(): Single<Response<MultiverseResponse>>
}