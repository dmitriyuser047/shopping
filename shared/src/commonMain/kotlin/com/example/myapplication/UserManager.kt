package com.example.myapplication

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val testKey: String? = null,
    val nameListProducts: String? = null
)

expect object UserManager {
    fun setTestKey(newTestKey: String)
    fun getTestKey(): String?
}