package com.example.myapplication.data

import com.example.myapplication.entity.ShoppingItem
import com.example.myapplication.entity.ShoppingList

interface ShoppingRepository {
    suspend fun createTestKey(): String
    suspend fun authenticate(): Boolean
    suspend fun createShoppingList(name: String): Int
    suspend fun removeShoppingList(listId: Int): Boolean
    suspend fun addToShoppingList(listId: Int,name: String, n: Int): Int
    suspend fun removeFromList(listId: Int, itemId: Int): Boolean
    suspend fun crossItOff(itemId: Int): Boolean
    suspend fun getAllMyShopLists(): List<ShoppingList>
    suspend fun getShoppingList(listId: Int): List<ShoppingItem>
}