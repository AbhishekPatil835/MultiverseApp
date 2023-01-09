package com.bookmyshow.multiverseofmovies.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bookmyshow.multiverseofmovies.android.usecases.MultiverseCastCrewResult
import com.bookmyshow.multiverseofmovies.android.usecases.MultiverseCastCrewUseCases
import com.bookmyshow.multiverseofmovies.android.usecases.MultiverseResult
import com.bookmyshow.multiverseofmovies.android.usecases.MultiverseUseCases
import com.bookmyshow.multiverseofmovies.base_framework.network.Response
import com.bookmyshow.multiverseofmovies.base_framework.rx.Disposer
import com.bookmyshow.multiverseofmovies.base_framework.rx.RxDisposer
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MultiverseViewModel @Inject constructor(
    private val multiverseUseCases: MultiverseUseCases,
    private val multiverseCastCrewUseCases: MultiverseCastCrewUseCases
) : ViewModel(), Disposer<Disposable> by RxDisposer() {

    private var _multiverseLiveData: MutableLiveData<Response<MultiverseResult>> = MutableLiveData()
    val multiverseLiveData: LiveData<Response<MultiverseResult>> = _multiverseLiveData

    fun getMultiverseDetails() {
        multiverseUseCases
            .invoke()
            .doOnSubscribe {
                _multiverseLiveData.postValue(Response.loading())
            }
            .subscribe(
                {
                    _multiverseLiveData.postValue(Response.success(it)) },
                {
                    _multiverseLiveData.postValue(Response.error(it))
                    Timber.e(it)

                }
            ).collect()
    }


    private var _multiverseCastCrewLiveData: MutableLiveData<Response<MultiverseCastCrewResult>> = MutableLiveData()
    val multiverseCastCrewLiveData: LiveData<Response<MultiverseCastCrewResult>> = _multiverseCastCrewLiveData

    fun getMultiverseCastCrewDetails() {
        multiverseCastCrewUseCases
            .invoke()
            .doOnSubscribe {
                _multiverseCastCrewLiveData.postValue(Response.loading())
            }
            .subscribe(
                {
                    _multiverseCastCrewLiveData.postValue(Response.success(it)) },
                {
                    _multiverseCastCrewLiveData.postValue(Response.error(it))
                    Timber.e(it)

                }
            ).collect()
    }
}