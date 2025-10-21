package com.dattran.unitconverter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class ShoppingListViewModel(private val shoppingRepository : ShoppingListRepository = ShoppingListRepository()) : ViewModel() {
    private val _shoppingList = MutableStateFlow<List<ShoppingItem>>(emptyList())
    val shoppingList: StateFlow<List<ShoppingItem>> = _shoppingList

    init {
        getShoppingList()
    }

    private fun getShoppingList() {
        _shoppingList.value = shoppingRepository.getItems().toList() // Tạo list mới
    }

    fun addItem(itemName : String, itemQuantity: String) {
        shoppingRepository.addItem(itemName, itemQuantity)
        getShoppingList()
    }

    fun updateItem(itemName : String, itemQuantity: String, id : Int) {
        shoppingRepository.updateItem(itemName, itemQuantity, id)
        getShoppingList()
    }

    fun deleteItem(id: Int) {
        shoppingRepository.deleteItem(id)
        getShoppingList()
    }

    fun findItem(search : String) : ShoppingItem? {
        return shoppingRepository.findItem(search)
    }
}