package com.example.mobiletest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletest.R
import com.example.mobiletest.databinding.CategoryItemBinding
import com.example.mobiletest.data.models.Category
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.squareup.picasso.Picasso

//адаптер для категорий
class CategoryAdapterDelegate(private val onItemClick: (Category) -> Unit) : AdapterDelegate<List<Any>>() {

    private inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = CategoryItemBinding.bind(itemView)
        fun bind(item: Category) = with(binding) {
            tvCategory.text = item.name
            Picasso.get().load(item.image_url).into(imView)
            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun isForViewType(items: List<Any>, position: Int): Boolean {
        return items[position] is Category
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(
        items: List<Any>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val category = items[position] as Category
        val viewHolder = holder as CategoryViewHolder
        viewHolder.bind(category)
    }
}