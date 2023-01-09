package com.bookmyshow.multiverseofmovies.network

import okhttp3.Interceptor

class OkHttpClientFactoryDependencyImpl (
): OkHttpClientFactory.Dependency {

    override fun getInterceptors(): List<Interceptor> {
        return listOf(
            HttpHeadersInterceptor()
        )
    }
}