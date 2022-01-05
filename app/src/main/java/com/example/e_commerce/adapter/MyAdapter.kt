package com.example.e_commerce.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.e_commerce.R
import com.example.e_commerce.model.Product

class MyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private var itemList: MutableList<Product> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.activity_single_product_element,
            parent, false)
        return  ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        when (holder)
        {
            is ViewHolder ->
            {
                holder.bind(itemList[position])
            }
        }
    }

    override fun getItemCount(): Int
    {
        return itemList.size
    }

    fun add(Log: Product)
    {
        itemList.add(0, Log)
        notifyItemInserted(0)
    }


    class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val name = itemView.findViewById<TextView>(R.id.productName)
        val price = itemView.findViewById<TextView>(R.id.price)
        val quantity = itemView.findViewById<TextView>(R.id.quantity)
        fun bind(product: Product)
        {
            name.text =     "Product Name: "  + product.name
            price.text = "Price: "+product.price
            quantity.text = "Quantity: "+product.quantity
        }
    }
}