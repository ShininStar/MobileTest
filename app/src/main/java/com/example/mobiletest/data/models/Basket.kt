package com.example.mobiletest.data.models

/*класс для хранения объектов в корзине, я понимаю, что это не самое изящное решение
* можно все сделать через базу данных, но решил показать и такой вариант*/
class Basket private constructor(){
    //списко куда сохраняем блюда
    private val items: MutableList<Dishe> = mutableListOf()

    //добавить блюдо
    fun addItem(dishe: Dishe) {
        items.add(dishe)
    }
    //удалить блюдо
    fun removeItem(dishe: Dishe) {
        items.remove(dishe)
    }
    /*возвращаем hashMap из списка, где количество повторов в списке, это
     количество заказанных блюд*/
    fun getItems(): HashMap<Dishe, Int> {
        val hashMap = HashMap<Dishe, Int>()
        for (dish in items) {
            val count = hashMap[dish] ?: 0
            hashMap[dish] = count + 1
        }
        return hashMap
    }
    //создаем синглтон, чтобы всегда возвращался один и тот же объект
    companion object {
        private var instance: Basket? = null
        fun getInstance(): Basket {
            if (instance == null) {
                synchronized(Basket::class) {
                    if (instance == null) {
                        instance = Basket()
                    }
                }
            }
            return instance!!
        }
    }
}