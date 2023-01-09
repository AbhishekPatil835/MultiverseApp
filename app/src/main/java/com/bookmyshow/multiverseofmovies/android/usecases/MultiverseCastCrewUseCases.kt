package com.bookmyshow.multiverseofmovies.android.usecases

import com.bookmyshow.multiverseofmovies.android.models.MultiverseCastResponse
import com.bookmyshow.multiverseofmovies.android.repository.MultiverseCastCrewRepository
import com.bookmyshow.multiverseofmovies.android.usecases.MultiverseCastCrewResult.*
import dagger.hilt.android.scopes.ViewModelScoped
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONObject
import java.net.HttpURLConnection
import javax.inject.Inject


@ViewModelScoped
class MultiverseCastCrewUseCases @Inject constructor(
    private val multiverseCastCrewRepository: MultiverseCastCrewRepository
) {
    fun invoke(
    ): Single<MultiverseCastCrewResult> {
        return multiverseCastCrewRepository.getMovieCastCrewDetails()
            .map {
                when {
                    it.isSuccessful -> MultiverseCastCrewSuccess(it.body()!!)
                    it.code() in listOf(
                        HttpURLConnection.HTTP_UNAUTHORIZED,
                        HttpURLConnection.HTTP_BAD_REQUEST
                    ) -> {
                        val tempErrorBody = it.errorBody()!!.string()
                        val jo = JSONObject(tempErrorBody)
                        val msg = jo.getString("status_message")
                        MultiverseCastCrewFailure(msg)
                    }
                    else -> UnknownError
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

sealed class MultiverseCastCrewResult {
    data class MultiverseCastCrewSuccess(val multiverseCastResponse: MultiverseCastResponse) : MultiverseCastCrewResult()
    data class MultiverseCastCrewFailure(val errorMessage: String) : MultiverseCastCrewResult()
    object UnknownError : MultiverseCastCrewResult()
}