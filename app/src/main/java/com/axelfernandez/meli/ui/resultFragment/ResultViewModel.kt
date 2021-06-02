package com.axelfernandez.meli.ui.resultFragment

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.axelfernandez.meli.api.ApiHelper
import com.axelfernandez.meli.models.ItemResponse
import com.axelfernandez.meli.repository.ResultRepository
import com.axelfernandez.meli.utils.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

class ResultViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val resultRepository: ResultRepository
) : ViewModel() {

    companion object{
        const val QUERY_SAVED = "querySaved"
        const val TITLE_STATUS = "titleStatus"
    }
    var items = MutableLiveData<Resource<ItemResponse>>()

    fun searchItem(search :String){
        viewModelScope.launch {
            items.postValue(Resource.loading())
            try {
                val response = resultRepository.searchItems(search)
                if (response.results.isEmpty()){
                    items.postValue(Resource.empty())
                }else{
                    items.postValue(Resource.success(response))
                }
            }catch (e: Exception){
                items.postValue(Resource.error(null,e.message!!))
            }
        }
    }

    var querySaved:String?
        set(value) = savedStateHandle.set(QUERY_SAVED,value)
        get() = savedStateHandle.get<String>(QUERY_SAVED)

    var titleStatus: Boolean
        set(value) = savedStateHandle.set(TITLE_STATUS,value)
        get() = savedStateHandle.get<Boolean>(TITLE_STATUS) ?: true



    class Factory(
        private val apiHelper: ApiHelper,
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle?
    ) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return ResultViewModel(handle,ResultRepository(apiHelper)) as T
        }


    }
}