package com.axelfernandez.meli.ui.itemDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.axelfernandez.meli.api.ApiHelper
import com.axelfernandez.meli.models.Item
import com.axelfernandez.meli.repository.DetailRepository
import com.axelfernandez.meli.utils.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import java.lang.Exception

class ItemDetailViewModel(
    private val detailRepository: DetailRepository
) : ViewModel() {

    fun getDetail(id : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            emit(Resource.success(data = detailRepository.getItemDetail(id)))
        }catch (e:Exception){
            emit(Resource.error(null, e.message!!))
        }
    }

    fun getDescription(id:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            emit(Resource.success(data = detailRepository.getItemDescription(id)))
        }catch (e:Exception){
            emit(Resource.error(null, e.message!!))
        }
    }

    class Factory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ItemDetailViewModel(DetailRepository(apiHelper)) as T
        }
    }
}