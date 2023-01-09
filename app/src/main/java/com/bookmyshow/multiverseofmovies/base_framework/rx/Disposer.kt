package com.bookmyshow.multiverseofmovies.base_framework.rx

interface Disposer<T> {
    fun T.collect()
    fun dispose()
}