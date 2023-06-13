package com.example.mobiletest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobiletest.R
import com.example.mobiletest.adapters.BasketAdapter
import com.example.mobiletest.databinding.FragmentBasketBinding
import com.example.mobiletest.data.models.Basket
import com.example.mobiletest.data.models.Dishe
import com.example.mobiletest.viewmodel.BasketViewModel

//фрагмент корзины
class BasketFragment : Fragment() {

    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: BasketAdapter
    private val model: BasketViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBasketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observe()
    }

    /*в функции инит достаем hashMap из объекта Basket, в которой хранятся выбранные продукты в
    * нужном количестве*/
    private fun init() = with(binding) {
        //возвращаем нужный hashMap
        val dishMap = Basket.getInstance().getItems()
        //отдельный адаптер для корзины, туда передаем модель чтобы следить из фрагмента за нажатиями
        //на кнопки + и -
        adapter = BasketAdapter(dishMap, model)
        rcViewBasket.layoutManager = LinearLayoutManager(activity)
        rcViewBasket.adapter = adapter
        //функция для обновления текста кнопки Оплата
        updatePayButton(dishMap)
    }
    //следим во вью модели за обновлением нашей hashMap
    private fun observe() {
        model.dishesInBasket.observe(viewLifecycleOwner) {
            adapter.updateData(it)
            updatePayButton(it)
        }
    }

    //функция для обновления кнопки оплаты с указанием суммы
    private fun updatePayButton(dishMap: HashMap<Dishe, Int>) = with(binding) {
        var totalPrice = 0

        for ((dish, count) in dishMap) {
            val price = dish.price
            totalPrice += price * count
        }
        val text = getString(R.string.pay) + " " + totalPrice + " " + getString(R.string.rub)
        btPay.text = text
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}