package com.axelfernandez.meli

import com.axelfernandez.meli.models.Description
import com.axelfernandez.meli.models.Item
import com.axelfernandez.meli.models.ItemResponse
import com.axelfernandez.meli.models.Page

object TestUtilities {

    fun getFakePage(): Page {
        return Page(10, 1)
    }

    fun getFakeDescription():Description{
        return Description("Some Description")
    }

    fun getFakeItemList():List<Item>{
        val list = ArrayList<Item>()
        list.add(
            getFakeItem()
        )
        return list
    }

    fun getFakeItem():Item{
        return Item(
            id = "1",
            title = "Something",
            price = 1.1,
            sold_quantity = 1,
            available_quantity = 1,
            condition = "new",
            pictures = null,
            thumbnail = ""
        )
    }
     fun getFakeItemResponse(): ItemResponse {
        return ItemResponse("query", getFakePage(), getFakeItemList())

    }
}