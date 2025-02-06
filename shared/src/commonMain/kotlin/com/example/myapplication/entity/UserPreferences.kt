package com.example.myapplication.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val testKey: String? = null,
)
