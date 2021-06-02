package com.axelfernandez.meli.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.adapters.SearchViewBindingAdapter
import androidx.navigation.findNavController
import com.axelfernandez.meli.R
import kotlinx.android.synthetic.main.app_bar_component.view.*

class AppBarComponent(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.app_bar_component, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.AppBarComponent, 0, 0)
            val text = typedArray.getString(R.styleable.AppBarComponent_title)
            val showArrow = typedArray.getBoolean(R.styleable.AppBarComponent_showArrow,false)
            val showSearch = typedArray.getBoolean(R.styleable.AppBarComponent_showSearch,false)
            typedArray.recycle()
            title_app_bar.text = text
            back_button.isVisible = showArrow
            search_view.isVisible = showSearch
            back_button.setOnClickListener {
                findNavController().popBackStack()
            }
            search_view.setOnSearchClickListener {
                showBackArrow = false
                showTitle = false
            }
            search_view.setOnCloseListener {
                showBackArrow = showArrow
                showTitle = true
                false
            }



        }
    }

    fun searchSetOnClickListener(clickListener: (it: String) -> Unit){
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {
                clickListener(p0?:return false)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })
    }

     var showBackArrow :Boolean
        set(value) {
            back_button.isVisible= value
        }
        get()= back_button.isVisible

     var showTitle :Boolean
        set(value) {
            title_app_bar.isVisible= value
        }
        get()= title_app_bar.isVisible


    var title: String
        set(value) {
            title_app_bar.text = value
        }
        get() = title_app_bar.text.toString()


}