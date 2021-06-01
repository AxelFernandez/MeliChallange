package com.axelfernandez.meli.repository

import com.axelfernandez.meli.api.ApiHelper


/*
    This Repository only have one source of data, and is the api online.
    If we want to add a database to persist data it should be done in this class
 */
class ResultRepository(private val apiHelper: ApiHelper) {

    suspend fun searchItems(search:String) = apiHelper.searchItem(search)

}