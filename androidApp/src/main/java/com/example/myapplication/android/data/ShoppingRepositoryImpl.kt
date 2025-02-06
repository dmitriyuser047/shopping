package com.example.myapplication.android.data

import com.example.myapplication.data.ShoppingApi
import com.example.myapplication.data.ShoppingRepository
import com.example.myapplication.UserManager
import com.example.myapplication.android.App
import com.example.myapplication.entity.ShoppingItem
import com.example.myapplication.entity.ShoppingList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingRepositoryImpl @Inject constructor(
): ShoppingRepository {

    private lateinit var authenticatedKey: String
    private lateinit var testKey: String

    override suspend fun createTestKey(): String {
        if (UserManager.getTestKey() == null) {
            testKey = ShoppingApi.createTestKey()
            UserManager.setTestKey(testKey)
        } else {
            testKey = UserManager.getTestKey().toString()
        }
        return testKey
    }

    override suspend fun authenticate(): Boolean {
        authenticatedKey = UserManager.getTestKey() ?: ""
        val response = ShoppingApi.authenticate(authenticatedKey)
        return response
    }

    override suspend fun createShoppingList(name: String): Int {
        return ShoppingApi.createShoppingList(authenticatedKey, name)
    }

    override suspend fun removeShoppingList(listId: Int): Boolean {
        return ShoppingApi.removeShoppingList(listId)
    }

    override suspend fun addToShoppingList(listId: Int, name: String, n: Int): Int {
        return ShoppingApi.addToShoppingList(listId, name, n)
    }

    override suspend fun removeFromList(listId: Int, itemId: Int): Boolean {
        return ShoppingApi.removeFromList(listId, itemId)
    }

    override suspend fun crossItOff(itemId: Int): Boolean {
        return ShoppingApi.crossItOff(itemId)
    }

    override suspend fun getAllMyShopLists(): List<ShoppingList> {
        return ShoppingApi.getAllMyShopLists(authenticatedKey)
    }

    override suspend fun getShoppingList(listId: Int): List<ShoppingItem> {
        return ShoppingApi.getShoppingList(listId)
    }
}