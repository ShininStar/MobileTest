package com.example.mobiletest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletest.R
import com.example.mobiletest.databinding.BasketItemBinding
import com.example.mobiletest.data.models.Basket
import com.example.mobiletest.data.models.Dishe
import com.example.mobiletest.viewmodel.BasketViewModel
import com.squareup.picasso.Picasso

/*адаптер для коризны решил сделать отдельно, так как в параметрах он принимает hasMap
* и viewmodel для того чтобы следить за нажатиями на + и -*/
class BasketAdapter(private var dishMap: HashMap<Dishe, Int>, private val model: BasketViewModel) :
    RecyclerView.Adapter<BasketAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = BasketItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.basket_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dishMap.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dish = dishMap.keys.elementAt(position)
        val count = dishMap[dish]

        holder.binding.tvName.text = dish.name
        Picasso.get().load(dish.image_url).into(holder.binding.imItem)
        holder.binding.tvPriceBasket.text = (dish.price * count!!).toString() + " ₽"
        holder.binding.tvWeightBasket.text = " · " + (dish.weight * count).toString() + "г"
        holder.binding.tvCount.text = count.toString()
        //по нажатию на минус удаляем элемент из списка
        holder.binding.btMinus.setOnClickListener {
            Basket.getInstance().removeItem(dish)
            model.dishesInBasket.value = Basket.getInstance().getItems()
        }
        //по нажатию на плюс добавляем элемент в список
        holder.binding.btPlus.setOnClickListener {
            Basket.getInstance().addItem(dish)
            model.dishesInBasket.value = Basket.getInstance().getItems()
        }
    }

    //когда hashMap меняется обновляем адаптер
    fun updateData(newDishMap : HashMap<Dishe, Int>) {
        dishMap = newDishMap
        notifyDataSetChanged()
    }
}