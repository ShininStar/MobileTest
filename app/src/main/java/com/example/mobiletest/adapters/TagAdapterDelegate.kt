package com.example.mobiletest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mobiletest.R
import com.example.mobiletest.databinding.TagItemBinding
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

//адаптер для тегов
class TagAdapterDelegate(private val onItemClick: (String) -> Unit) : AdapterDelegate<List<Any>>() {

    //по умолчанию первая кнопка Все продкуты будет нажата
    private var selectedButtonIndex = 0

    private inner class TagsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = TagItemBinding.bind(itemView)
        fun bind(item: String, position: Int, holder: ViewHolder) = with(binding) {
            btTag.text = item
            btTag.setOnClickListener {
                onItemClick(item)
                selectedButtonIndex = position
            }
            /*в зависимости от того нажат кнопка или нет у нее будет меняться
            * цвет фона и текста, для этого и передаем сюда контекст*/
            if (position == selectedButtonIndex) {
                btTag.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
                btTag.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.blue))
            }
            else {
                btTag.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
                btTag.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.gray))
            }
        }
    }

    override fun isForViewType(items: List<Any>, position: Int): Boolean {
        return items[position] is String
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tag_item, parent, false)
        return TagsViewHolder(view)
    }

    override fun onBindViewHolder(
        items: List<Any>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val tag = items[position] as String
        val tagsViewHolder = holder as TagsViewHolder
        tagsViewHolder.bind(tag, position, holder)
    }
}