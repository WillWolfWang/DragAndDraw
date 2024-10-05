package com.will.draganddraw.toolbar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.will.draganddraw.R

class FruitAdapter(val context: Context, val fruitList: List<Fruit>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fruit_item, parent, false)
        return FruitViewHolder(view)
    }

    override fun getItemCount(): Int {
        return fruitList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val fruit = fruitList.get(position)
        val h = holder as FruitViewHolder
        h.bindHolder(fruit)
    }

    inner class FruitViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        val fruitImage: ImageView = view.findViewById(R.id.fruitImage)
        val fruitName: TextView = view.findViewById(R.id.fruitName)

        init {
            view.setOnClickListener(this)
        }

        fun bindHolder(fruit: Fruit) {
            fruitName.text = fruit.name
            Glide.with(fruitImage).load(fruit.imageId).into(fruitImage)
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            val fruit = fruitList.get(position)
            FruitDetailActivity.startFruitDetailActivity(context, fruit.name, fruit.imageId)
        }
    }
}