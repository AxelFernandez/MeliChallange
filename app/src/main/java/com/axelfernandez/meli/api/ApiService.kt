package com.axelfernandez.meli.api

import com.axelfernandez.meli.models.Description
import com.axelfernandez.meli.models.Item
import com.axelfernandez.meli.models.ItemResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("sites/MLA/search?")
    suspend fun findItems(@Query("q")search: String): ItemResponse

    @GET("items/{id}")
    suspend fun getItemDetail(@Path("id") itemId: String): Item

    @GET("items/{id}/description")
    suspend fun getItemDescription(@Path("id") itemId: String): Description
}