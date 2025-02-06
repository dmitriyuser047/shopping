package com.example.myapplication.data

import com.example.myapplication.entity.AddToShoppingListResponse
import com.example.myapplication.entity.ApiResponse
import com.example.myapplication.entity.CreateShoppingListResponse
import com.example.myapplication.entity.GetAllMyShopListsResponse
import com.example.myapplication.entity.GetShoppingListResponse
import com.example.myapplication.entity.RemoveShoppingListResponse
import com.example.myapplication.entity.ShoppingItem
import com.example.myapplication.entity.ShoppingList
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ShoppingApi {
    private const val BASE_URL = "https://cyberprot.ru/shopping/v2/"

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { isLenient = true; ignoreUnknownKeys = true })
        }
    }


    suspend fun createTestKey(): String {
        return client.get(BASE_URL + "CreateTestKey?").body<String>().trim('"')
    }

    // Аутентификация по ключу
    suspend fun authenticate(key: String): Boolean {
        val response: ApiResponse = client.get(BASE_URL + "Authentication") {
            parameter("key", key)
        }.body()
        println("response")
        return response.success
    }

    // Создание списка покупок
    suspend fun createShoppingList(key: String, name: String): Int {
        val response: CreateShoppingListResponse = client.post(BASE_URL + "CreateShoppingList") {
            parameter("key", key)
            parameter("name", name)
        }.body()
        return response.listId
    }

    // Удаление списка покупок
    suspend fun removeShoppingList(listId: Int): Boolean {
        val response: RemoveShoppingListResponse = client.post(BASE_URL + "RemoveShoppingList") {
            parameter("list_id", listId)
        }.body()
        return response.newValue
    }

    // Добавление товара в список покупок
    suspend fun addToShoppingList(listId: Int, name: String, n: Int): Int {
        val response: AddToShoppingListResponse = client.post(BASE_URL + "AddToShoppingList") {
            parameter("id", listId)
            parameter("value", name)
            parameter("n", n)
        }.body()
        return response.itemId
    }

    // Удаление товара из списка покупок
    suspend fun removeFromList(listId: Int, itemId: Int): Boolean {
        val response: ApiResponse = client.post(BASE_URL + "RemoveFromList") {
            parameter("list_id", listId)
            parameter("item_id", itemId)
        }.body()
        return response.success
    }

    // Вычеркивание товара из списка покупок
    suspend fun crossItOff(itemId: Int): Boolean {
        val response: ApiResponse = client.post(BASE_URL + "CrossItOff") {
            parameter("id", itemId)
        }.body()
        return response.success
    }

    // Получение всех списков покупок
    suspend fun getAllMyShopLists(key: String): List<ShoppingList> {
        val response: GetAllMyShopListsResponse = client.get(BASE_URL + "GetAllMyShopLists") {
            parameter("key", key)
        }.body()
        return response.shopLists
    }
    // Получить список продуктов
    suspend fun getShoppingList(listId: Int): List<ShoppingItem> {
        val response: GetShoppingListResponse = client.get(BASE_URL + "GetShoppingList") {
            parameter("list_id", listId)
        }.body()
        return response.itemList
    }
}