package com.axelfernandez.meli.ui.itemDetail

import androidx.lifecycle.*
import com.axelfernandez.meli.api.ApiHelper
import com.axelfernandez.meli.models.Description
import com.axelfernandez.meli.models.Item
import com.axelfernandez.meli.repository.DetailRepository
import com.axelfernandez.meli.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemDetailViewModel(
    private val detailRepository: DetailRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    var item = MutableLiveData<Resource<Item>>()
    var description = MutableLiveData<Resource<Description>>()

    fun getDetail(id: String) {
        viewModelScope.launch(dispatcher) {
            item.postValue(Resource.loading())
            try {
                item.postValue(Resource.success(data = detailRepository.getItemDetail(id)))
            } catch (e: Exception) {
                item.postValue(Resource.error(null, e.message ?: "getDetail Exception"))
            }
        }

    }

    fun getDescription(id: String){
        viewModelScope.launch(dispatcher) {
            description.postValue(Resource.loading())
            try {
                description.postValue(Resource.success(data = detailRepository.getItemDescription(id)))
            } catch (e: Exception) {
                description.postValue(Resource.error(null, e.message ?: "getDescription Exception"))
            }
        }
    }

    class Factory(
        private val apiHelper: ApiHelper,
        private val dispatcher: CoroutineDispatcher
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ItemDetailViewModel(DetailRepository(apiHelper), dispatcher) as T
        }
    }
}