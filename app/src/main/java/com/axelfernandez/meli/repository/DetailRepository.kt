package com.axelfernandez.meli.repository

import com.axelfernandez.meli.api.ApiHelper

class DetailRepository(private val apiHelper: ApiHelper) {

    suspend fun getItemDetail(id: String) = apiHelper.getItemDetail(id)

    suspend fun getItemDescription(id: String) = apiHelper.getItemDescription(id)
}