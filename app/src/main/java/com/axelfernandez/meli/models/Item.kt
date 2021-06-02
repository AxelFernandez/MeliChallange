package com.axelfernandez.meli.models


data class ItemResponse(
    val query: String,
    val paging: Page,
    val results: List<Item>
)

data class Item(
    val id: String,
    val title: String,
    val price: Double,
    val sold_quantity: Int,
    val available_quantity: Int,
    val condition: String,
    val pictures: List<Pictures>?,
    val thumbnail: String
)

data class Page(
    val total: Int,
    val offset: Int
)


data class Pictures(
    val id: String,
    val url: String
)

data class Description(
    val plain_text :String
)