package com.axelfernandez.meli.utils

/*
   This is the class that is responsible for creating the communication channels that will then
   be updated by a liveData for the different states in which the call to the api can be found.
 */
data class Resource<out T>(val status: Status, val data: T?, val messageError: String? = null) {

    companion object {
        fun <T> loading(): Resource<T> = Resource(Status.LOADING, null)
        fun <T> success(data: T): Resource<T> = Resource(Status.SUCCESS, data)
        fun <T> error(data: T?, messageError: String?): Resource<T> = Resource(Status.ERROR, data, messageError)


    }
}
