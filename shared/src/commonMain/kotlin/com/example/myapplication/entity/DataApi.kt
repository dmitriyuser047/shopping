package com.example.myapplication.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val success: Boolean,
)

@Serializable
data class CreateShoppingListResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("list_id") val listId: Int
)

@Serializable
data class RemoveShoppingListResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("new_value") val newValue: Boolean
)

@Serializable
data class AddToShoppingListResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("item_id") val itemId: Int
)

@Serializable
data class GetAllMyShopListsResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("shop_list") val shopLists: List<ShoppingList>
)

@Serializable
data class ShoppingList(
    @SerialName("created") val created: String,
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int
)

@Serializable
data class GetShoppingListResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("item_list") val itemList: List<ShoppingItem>
)

@Serializable
data class ShoppingItem(
    @SerialName("created") val created: String,
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
    @SerialName("is_crossed") val isCrossed: Boolean
)