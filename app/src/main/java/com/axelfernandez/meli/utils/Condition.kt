package com.axelfernandez.meli.utils

enum class Condition(){
    new{
        override fun getTranslation(): String {
            return "Nuevo"
        }
    },
    used {
        override fun getTranslation(): String {
            return "Usado"
        }
    },
    not_specified{
        override fun getTranslation(): String {
            return "No Especificado"
        }
    };
    abstract fun getTranslation():String
}