package com.axelfernandez.meli.utils

enum class Condition(val value: String){
    NEW("new"){
        override fun getTranslation(): String {
            return "Nuevo"
        }
    },
    USED("used") {
        override fun getTranslation(): String {
            return "Usado"
        }
    },
    NOT_SPECIFY("not_specify"){
        override fun getTranslation(): String {
            return "No Especificado"
        }
    };
    companion object {
        fun from(value: String): Condition {
            return values().first { it.value == value }
        }
    }
    abstract fun getTranslation():String
}