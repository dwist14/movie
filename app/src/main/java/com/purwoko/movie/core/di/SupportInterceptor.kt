package com.purwoko.movie.core.di
import okhttp3.Interceptor
import okhttp3.Response

class SupportInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4MTIyODBlMTEwNWU0YzA5ODg5ZDAzYTNkZDhkYzFmOSIsInN1YiI6IjVlNzgyZGM4MmYzYjE3MDAxMTUzNDZlMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.V3HK_OmPevY82FwNkW_carDsJ_tQk2sj-AhiTAFA-To")
            .header("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }
}