package com.axelfernandez.meli.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.axelfernandez.meli.R
import com.axelfernandez.meli.models.Pictures
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image.view.*

class ImageCarrouselAdapter(
    private val items: List<Pictures>,
    private val context: Context
) : RecyclerView.Adapter<ImageCarrouselAdapter.ImageCarrouselViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageCarrouselViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ImageCarrouselViewHolder(
            layoutInflater.inflate(
                R.layout.item_image,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageCarrouselViewHolder, position: Int) {
        holder.bind(items[position],context)
    }

    override fun getItemCount(): Int {
       return items.size
    }
    class ImageCarrouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Pictures, context: Context){
            Picasso.with(context).load(item.url).into(itemView.item_image)
        }

    }
}