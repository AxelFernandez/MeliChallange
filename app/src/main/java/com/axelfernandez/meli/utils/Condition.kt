package com.axelfernandez.meli.utils

enum class Condition(){
    new{
        override fun getTranslation(): String {
            return "Nuevo"
        }
    },
    used{
        override fun getTranslation(): String {
            return "Usado"
        }
    };
    abstract fun getTranslation():String
}