package com.axelfernandez.meli.api

import com.axelfernandez.meli.models.ItemResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("sites/MLA/search?")
    suspend fun findItems(@Query("q")search: String): ItemResponse
}