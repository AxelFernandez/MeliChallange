package com.axelfernandez.meli.ui.resultFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.axelfernandez.meli.api.ApiHelper
import com.axelfernandez.meli.models.ItemResponse
import com.axelfernandez.meli.repository.ResultRepository
import com.axelfernandez.meli.utils.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

class ResultViewModel(
    private val resultRepository: ResultRepository
) : ViewModel() {

    var items = MutableLiveData<Resource<ItemResponse>>()

    fun searchItem(search :String){
        viewModelScope.launch {
            items.postValue(Resource.loading())
            try {
                items.postValue(Resource.success(resultRepository.searchItems(search)))
            }catch (e: Exception){
                items.postValue(Resource.error(null,e.message!!))
            }
        }
    }


    class Factory(
        private val apiHelper: ApiHelper
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ResultViewModel(ResultRepository(apiHelper)) as T
        }
    }
}