package com.axelfernandez.meli.api

import android.content.Context
import com.axelfernandez.meli.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.HOST_NAME) // change this in Gradle files
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

       fun buildService(): ApiService {
            return getRetrofit().create(ApiService::class.java)
        }

}