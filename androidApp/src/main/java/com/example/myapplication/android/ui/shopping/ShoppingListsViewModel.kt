package com.example.myapplication.android.ui.shopping

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.android.data.ShoppingRepositoryImpl
import com.example.myapplication.data.ShoppingRepository
import com.example.myapplication.entity.ShoppingItem
import com.example.myapplication.entity.ShoppingList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import javax.inject.Inject

@HiltViewModel
class ShoppingListsViewModel @Inject constructor(
    private val repository: ShoppingRepositoryImpl
) : ViewModel() {

    private val _shopLists = MutableStateFlow<List<ShoppingList>>(emptyList())
    val shopLists: StateFlow<List<ShoppingList>> = _shopLists

    private val _shoppingItems = MutableStateFlow<Map<Int, List<ShoppingItem>>>(emptyMap())
    val shoppingItems: StateFlow<Map<Int, List<ShoppingItem>>> = _shoppingItems

    init {
        loadShoppingLists()
    }

    fun crossOffItem(itemId: Int) {
        viewModelScope.launch {
            val success = repository.crossItOff(itemId)
            if (success) {
                loadShoppingLists()
            }
        }
    }

    fun createShoppingList(name: String) {
        viewModelScope.launch {
            repository.createShoppingList(name)
            loadShoppingLists()
        }
    }

    fun removeShoppingList(listId: Int) {
        viewModelScope.launch {
            repository.removeShoppingList(listId)
            loadShoppingLists()
        }
    }

    fun addToShoppingList(listId: Int, name: String, quantity: Int) {
        viewModelScope.launch {
            repository.addToShoppingList(listId, name, quantity)
            loadShoppingLists()
        }
    }

    fun removeFromShoppingList(listId: Int, itemId: Int) {
        viewModelScope.launch {
            val success = repository.removeFromList(listId, itemId)
            if (success) {
                val updatedItems = shoppingItems.value.toMutableMap()
                updatedItems[listId] = updatedItems[listId]?.filterNot { it.id == itemId } ?: emptyList()
                _shoppingItems.value = updatedItems
            }
        }
    }

    fun loadShoppingListItems(listId: Int) {
        viewModelScope.launch {
            val response = repository.getShoppingList(listId)
            if (response.isNotEmpty()) {
                val updatedItems = _shoppingItems.value.toMutableMap()
                updatedItems[listId] = response
                _shoppingItems.value = updatedItems
            }
        }
    }

    fun loadShoppingLists() {
        viewModelScope.launch {
            val lists = repository.getAllMyShopLists()
            _shopLists.value = lists

            lists.forEach { list ->
                loadShoppingListItems(list.id)
            }
        }
    }

}