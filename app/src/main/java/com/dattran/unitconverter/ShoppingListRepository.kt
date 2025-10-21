package com.dattran.unitconverter

data class ShoppingItem(
    val id: Int,
    val name: String,
    val quantity: String,
)

class ShoppingListRepository {
    val item1 = ShoppingItem(id = 1, name = "Item 1", quantity = "101")
    val item2 = ShoppingItem(id = 2, name = "Item 2", quantity = "1222")
    val item3 = ShoppingItem(id = 3, name = "Item 3", quantity = "1223")
    val sListShoppingItem : MutableList<ShoppingItem> = mutableListOf<ShoppingItem>(item1, item2, item3)

    fun getItems() : List<ShoppingItem> {
        return sListShoppingItem;
    }

    fun findItem(name: String): ShoppingItem? {
        return sListShoppingItem.firstOrNull {
            it.name.contains(name, ignoreCase = true)
        }
    }

    fun addItem(itemName : String, itemQuantity: String) {
        val newId = if (sListShoppingItem.isEmpty()) 1 else sListShoppingItem.maxOf { it.id } + 1
        sListShoppingItem.add(ShoppingItem(
            id = newId,
            name = itemName,
            quantity = itemQuantity,
        ))
    }

    fun updateItem(itemName : String, itemQuantity: String, id : Int) {
        val index = sListShoppingItem.indexOfFirst { it.id == id }
        if (index != -1) {
            sListShoppingItem[index] = sListShoppingItem[index].copy(
                name = itemName, 
                quantity = itemQuantity
            )
        }
    }

    fun deleteItem(id: Int) {
        val index = sListShoppingItem.indexOfFirst { it.id == id }
        if (index != -1) {
            sListShoppingItem.removeAt(index)
        }
    }
}