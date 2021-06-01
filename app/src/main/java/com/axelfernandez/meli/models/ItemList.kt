package com.axelfernandez.meli.models


data class ItemResponse(
    val query: String,
    val paging: Page,
    val results: List<ItemList>
)

/*
    This class is open to be able to perform an inheritance with ItemDetail that shares the same
    attributes but the images are added.
 */
open class ItemList(
    val id: String = "",
    val title: String = "",
    val price: Double = 0.0,
    val sold_quantity: Int = 0,
    val available_quantity: Int = 0,
    val condition: String = "",
    val thumbnail: String = ""
)

data class Page(
    val total: Int,
    val offset: Int
)