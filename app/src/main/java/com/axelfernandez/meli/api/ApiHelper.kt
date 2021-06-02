package com.axelfernandez.meli.api

/*
    Pass the RetrofitBuilder.buildService() here
    This class is necessary because without it we should create the Retrofit object thus generating
    a dependency on the class
 */
class ApiHelper(private val apiService: ApiService) {

    suspend fun searchItem(search: String) = apiService.findItems(search)

    suspend fun getItemDetail(id: String) = apiService.getItemDetail(id)

    suspend fun getItemDescription(id: String) = apiService.getItemDescription(id)

}