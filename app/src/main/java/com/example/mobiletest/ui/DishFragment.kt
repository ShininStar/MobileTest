package com.example.mobiletest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobiletest.R
import com.example.mobiletest.adapters.MainAdapter
import com.example.mobiletest.databinding.FragmentCategoryBinding
import com.example.mobiletest.databinding.FragmentDishBinding
import com.example.mobiletest.data.models.Category
import com.example.mobiletest.data.models.Dishe
import com.example.mobiletest.viewmodel.DishesViewModel
import com.example.mobiletest.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DishFragment : Fragment() {

    private var _binding: FragmentDishBinding? = null
    private val binding get() = _binding!!
    private lateinit var dishesAdapter: MainAdapter
    private lateinit var tagsAdapter: MainAdapter
    private val model: DishesViewModel by activityViewModels()
    private var tagedDishes: List<Dishe>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observe()
        getDishes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //достаем объекты Dishe из вьюмодели
    private fun getDishes() {
        CoroutineScope(Dispatchers.IO).launch {
            model.getDishes()
        }
    }

    /*в функции инит инициализируем адаптеры для тегов и блюд для разных recycler view*/
    private fun init() = with(binding) {
        dishesAdapter = MainAdapter(requireContext()) { dishe ->
            //обрабатываем нажатие на блюдо, в DishDetailDialog передаем Dishe, на который нажали
            val dialog = DishDetailsDialog.newInstance(dishe as Dishe)
            //показываем диалог с информацией о блюде и возможностью положить в корзину
            dialog.show(requireActivity().supportFragmentManager, "dish_detail_dialog")
        }
        tagsAdapter = MainAdapter(requireContext()) { tag ->
            //обрабатываем нажатие на тэг, чтобы блюда фильтровались по тегу
            onTagClick(tag)
        }
        //rc для тегов с горизонтальной прокруткой
        rcViewTags.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcViewTags.adapter = tagsAdapter
        //rc для блюд по три в ряду с вертикальной прокруткой
        rcView.layoutManager = GridLayoutManager(activity, 3)
        rcView.adapter = dishesAdapter
    }

    //здесь следим за вью моделью
    private fun observe() {
        model.liveDataDishes.observe(viewLifecycleOwner) { dishesModel ->
            /*когда получаем DisheModel пробегаемся по всем элементам фильтруем уникальные теги и
            добавляем их в списко*/
            val list = dishesModel.dishes
                .flatMap { it.tegs }
                .distinct()
                .toList()
            //добавляем наш отфильтрованный списко тегов в адаптер
            tagsAdapter.items = list
            //добавляем списко блюд в адаптер
            tagedDishes = dishesModel.dishes
            dishesAdapter.items = tagedDishes
            tagsAdapter.notifyDataSetChanged()
            dishesAdapter.notifyDataSetChanged()
        }
    }

    //здесь обрабатываем нажатие на тэг
    private fun onTagClick (tag: Any) {
        //фильтруем списко по нажатому тегу
        val filteredList = tagedDishes?.filter {
            it.tegs.contains(tag)
        }
        //отправляем в адаптер отфильтрованный список и обновляем адаптеры
        dishesAdapter.items = filteredList
        dishesAdapter.notifyDataSetChanged()
        tagsAdapter.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance() = DishFragment()
    }
}