package com.axelfernandez.meli.models

class ItemDetail(
    private val pictures :List<Pictures>
):ItemList()

data class Pictures(
    val id: String,
    val url: String
)

data class Description(
    val plain_text :String
)