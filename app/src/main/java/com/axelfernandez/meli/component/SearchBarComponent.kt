package com.axelfernandez.meli.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.axelfernandez.meli.R
import kotlinx.android.synthetic.main.search_component.view.*

class SearchBarComponent(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {


    init {
        LayoutInflater.from(context)
            .inflate(R.layout.search_component, this, true)
    }

    fun getTextSearched(): String {
        return text_search.text.toString()
    }

    fun searchClickListener(clickListener: (it: View) -> Unit) {
        search_button.setOnClickListener(clickListener)
    }

    fun checkIfTextIsNullOrEmpty(): Boolean {
        if (text_search.text.isNullOrEmpty()) {
            text_search.error = "Debes Buscar algo antes, animate!"
            return false
        }
        return true
    }


}