package com.example.mobiletest.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.mobiletest.R
import com.example.mobiletest.data.models.Basket
import com.example.mobiletest.data.models.Dishe
import com.squareup.picasso.Picasso

/*Это всплывающий диалог по нажатию на блюдо, здесь не использовал binding, чтобы показать разные способы
* инициализации view*/
class DishDetailsDialog : DialogFragment() {

    private lateinit var imDishe: ImageView
    private lateinit var tvDishName: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvWeight: TextView
    private lateinit var tvDescription: TextView
    private lateinit var btToBag: Button
    private lateinit var btClose: ImageButton
    private lateinit var btLike: ImageButton

    companion object {
        private const val ARG_DISH = "dish"

        /*фгнкция инициализации DishDetailsDialog, сюда приходит объект на который нажали и сохраняется
        * в аргументы*/
        fun newInstance (dish: Dishe) : DishDetailsDialog {
            val args = Bundle()
            args.putParcelable(ARG_DISH, dish)

            val fragment = DishDetailsDialog()
            fragment.arguments = args
            return fragment
        }
    }

    /*создаем AletrDialog, надуваем разметку для всплывающего окна*/
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_dish_details, null)
        bind(dialogView)
        builder.setView(dialogView)
        val dialog = builder.create()
        updateDishCard(dialog)
        return dialog
    }

    /*здесь получаем объект из аргумента и заполняем с помощью него высплывающую карточку, а также
    * устанавливаем слушатели нажатий на кнопки закрыть, избранное и в корзину*/
    private fun updateDishCard(dialog: AlertDialog) {
        val dish = arguments?.getParcelable<Dishe>(ARG_DISH)
        if (dish != null) {
            val price = dish.price.toString() + " " + getString(R.string.rub)
            val weight = " " + getString(R.string.dot) + " " + dish.weight.toString() + getString(R.string.gramm)
            Picasso.get().load(dish.image_url).into(imDishe)
            tvDishName.text = dish.name
            tvPrice.text = price
            tvWeight.text = weight
            tvDescription.text = dish.description
            //закрываем диалог на кнопку
            btClose.setOnClickListener {
                dialog.dismiss()
            }
            btLike.setOnClickListener {

            }
            //добавляем продукт в корзину и закрываем диалог
            btToBag.setOnClickListener {
                val basket = Basket.getInstance()
                basket.addItem(dish)
                dialog.dismiss()
            }
        }
    }

    private fun bind(view: View) {
        imDishe = view.findViewById(R.id.imDish)
        tvDishName = view.findViewById(R.id.tvDishName)
        tvPrice = view.findViewById(R.id.tvPrice)
        tvWeight = view.findViewById(R.id.tvWeight)
        tvDescription = view.findViewById(R.id.tvDescriotion)
        btToBag = view.findViewById(R.id.btToBag)
        btClose = view.findViewById(R.id.btClose)
        btLike = view.findViewById(R.id.btLike)
    }
}