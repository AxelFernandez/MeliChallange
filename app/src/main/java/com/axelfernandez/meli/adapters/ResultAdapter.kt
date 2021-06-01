package com.axelfernandez.meli.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.axelfernandez.meli.R
import com.axelfernandez.meli.models.ItemList
import com.axelfernandez.meli.utils.Condition
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product.view.*

class ResultAdapter (
    private val items:List<ItemList> = ArrayList(),
    private val context: Context,
    private val itemClickListener: (ItemList) -> Unit
        ): RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ResultViewHolder(
            layoutInflater.inflate(
                R.layout.item_product,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(items[position],itemClickListener,context)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item :ItemList, itemClickListener: (ItemList) -> Unit, context: Context){
            itemView.condition.text = Condition.valueOf(item.condition).getTranslation()
            itemView.item_title.text = item.title
            itemView.price.text = context.getString(R.string.item_price, item.price.toInt().toString())
            itemView.quantity_available.text = context.getString(R.string.item_quantity_available,item.available_quantity.toString())
            itemView.quantity_sold.text = context.getString(R.string.item_quantity_sold, item.sold_quantity.toString())
            itemView.card_view.setOnClickListener{itemClickListener(item)}
            Picasso.with(context).load(item.thumbnail).into(itemView.item_image)
        }
    }
}