package com.example.mobiletest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobiletest.R
import com.example.mobiletest.data.models.Category
import com.example.mobiletest.adapters.MainAdapter
import com.example.mobiletest.databinding.FragmentCategoryBinding
import com.example.mobiletest.viewmodel.CategoryViewModel
import com.example.mobiletest.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//это стартовый фрагмент, здесь отображатся категории, которые берем с сервера
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainAdapter: MainAdapter
    private val model: CategoryViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        init()
        getCategories()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //берем категории из вью модели
    private fun getCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            model.getCategory()
        }
    }

    /*инициализируем адаптер, по нажатию на категорию передаем название категории через sharedViewModel
    * на следующий экран, чтобы оно отображалось в верхней панели*/
    private fun init() = with(binding) {
        mainAdapter = MainAdapter {item ->
            if (item is Category) {
                onCategoryClick()
                sharedViewModel.data.value = item.name
            }
        }
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = mainAdapter
    }

    //наблюаем за моделью на случай обновления данных
    private fun observe() {
        model.liveDataCategory.observe(viewLifecycleOwner) {categoryModel ->
            mainAdapter.items = categoryModel.categories
            mainAdapter.notifyDataSetChanged()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CategoryFragment()
    }

    //по клику на категорию переходим во фрагмент с блюдами
    fun onCategoryClick() {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentHolder, DishFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }
}