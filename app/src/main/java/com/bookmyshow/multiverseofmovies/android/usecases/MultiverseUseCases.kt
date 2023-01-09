package com.bookmyshow.multiverseofmovies.android.usecases

import com.bookmyshow.multiverseofmovies.android.models.MultiverseResponse
import com.bookmyshow.multiverseofmovies.android.repository.MultiverseRepository
import com.bookmyshow.multiverseofmovies.android.usecases.MultiverseResult.*
import dagger.hilt.android.scopes.ViewModelScoped
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONObject
import java.net.HttpURLConnection
import javax.inject.Inject


@ViewModelScoped
class MultiverseUseCases @Inject constructor(
    private val multiverseRepository: MultiverseRepository
) {
    fun invoke(
    ): Single<MultiverseResult> {
        return multiverseRepository.getMovieDetails()
            .map {
                when {
                    it.isSuccessful -> MultiverseSuccess(it.body()!!)
                    it.code() in listOf(
                        HttpURLConnection.HTTP_UNAUTHORIZED,
                        HttpURLConnection.HTTP_BAD_REQUEST
                    ) -> {
                        val tempErrorBody = it.errorBody()!!.string()
                        val jo = JSONObject(tempErrorBody)
                        val msg = jo.getString("status_message")
                        MultiverseFailure(msg)
                    }
                    else -> UnknownError
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

sealed class MultiverseResult {
    data class MultiverseSuccess(val multiverseResponse: MultiverseResponse) : MultiverseResult()
    data class MultiverseFailure(val errorMessage: String) : MultiverseResult()
    object UnknownError : MultiverseResult()
}