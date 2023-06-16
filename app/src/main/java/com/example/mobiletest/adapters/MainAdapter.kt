package com.example.mobiletest.adapters

import android.content.Context
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

/*в нашей главный адаптер добаляем через addDelegate наши адаптеры для категорий,
* блюд и тегов, чтобы в зависимости от входящего параметра использовался нужный
* здесь же прокидываем дальше обработчик нажатий*/
class MainAdapter(private val onItemClick: (Any) -> Unit) :
    ListDelegationAdapter<List<Any>>() {
    init {
        delegatesManager.addDelegate(CategoryAdapterDelegate { category ->
            onItemClick(category)
        })
        delegatesManager.addDelegate(DishesAdapterDelegate {dishe ->
            onItemClick(dishe)
        })
        delegatesManager.addDelegate(TagAdapterDelegate {tag ->
            onItemClick(tag)
        })
    }
}
