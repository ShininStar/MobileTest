package com.example.mobiletest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.mobiletest.R
import com.example.mobiletest.databinding.ActivityMainBinding
import com.example.mobiletest.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

/*Я начинающий разработчик и, наверняка, не все мои решения здесь выглядят изящно, но я хотел показать
* максимум своих возможностей, разные подходы к решению задач.*/
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //подключаем кнопки через binding
    private lateinit var binding: ActivityMainBinding
    //эта вьюмодель нужна для обновления верхней панели с городом, временем и фото аккаунта
    val shareViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(CategoryFragment())
        updateTopBar()
        observe()
        bottomNavPress()
    }
    /*устанавливаем слушатель нажатий на bottom navigation, по нажатию переходим в нужный фрагмент
    фрагменты поиска и аккаунта я не создавал*/
    private fun bottomNavPress() {
        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.category_item -> {
                    replaceFragment(CategoryFragment())
                    updateTopBar()
                }
//                R.id.search_item -> replaceFragment()
                R.id.basket_item -> {
                    replaceFragment(BasketFragment())
                    updateTopBar()
                }
//                R.id.acount_item -> replaceFragment()
                else -> {}
            }
            true
        }
    }

    //следим за обновлением вьюмодели и в зависимости от экрана делаем видимыми нужные элементы
    private fun observe() = with(binding){
        shareViewModel.data.observe(this@MainActivity) {categoryName ->
            tvCategoryName.visibility = View.VISIBLE
            btBack.visibility = View.VISIBLE
            btBack.setOnClickListener {
                onBackPressed()
            }
            tvCategoryName.text = categoryName
            imLocation.visibility = View.GONE
            tvDate.visibility = View.GONE
            tvLocation.visibility = View.GONE
        }
    }

    /*функция для обновления верхней панели, город написал хардкодом и фотку загрузил напрямую
    город можно брать из гео, ну и понятно фото тоже будет загружать пользователь эти функции я не
    реализовывал*/
    private fun updateTopBar() = with(binding){
        tvCategoryName.visibility = View.GONE
        btBack.visibility = View.GONE
        imLocation.visibility = View.VISIBLE
        tvDate.visibility = View.VISIBLE
        tvLocation.visibility = View.VISIBLE
        profileImage.setImageResource(R.drawable.my_img)
        tvDate.text = getCurrentTime()
        tvLocation.text = "Санкт-Петербург"
    }

    //когда нажимаем кнопку назад экран обновляется
    override fun onBackPressed() {
        super.onBackPressed()
        updateTopBar()
    }

    //текущее время
    private fun getCurrentTime() : String {
        val formatter = SimpleDateFormat("dd MMMM, yyyy", Locale("ru", "RU"))
        val cv = Calendar.getInstance()
        return formatter.format(cv.time)
    }

    //функция для перемещения между фрагментами
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentHolder, fragment)
            .commit()
    }
}