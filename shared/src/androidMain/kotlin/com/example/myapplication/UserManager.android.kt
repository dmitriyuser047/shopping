package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

actual object UserManager {

    private var preferencesFile: File? = null

    fun initialize(context: Any) {
        if (context is Context) {
            preferencesFile = File(context.filesDir, "user_preferences.json")
        } else {
            throw IllegalArgumentException("Expected context of type Android Context")
        }
    }

    private fun readPreferences(): UserPreferences {
        if (preferencesFile?.exists() == true) {
            val json = preferencesFile?.readText()
            return Json.decodeFromString(json ?: "")
        }
        return UserPreferences()
    }

    private fun writePreferences(preferences: UserPreferences) {
        val json = Json.encodeToString(preferences)
        preferencesFile?.writeText(json)
    }

    actual fun setTestKey(newTestKey: String) {
        val preferences = readPreferences()
        val updatedPreferences = preferences.copy(testKey = newTestKey)
        writePreferences(updatedPreferences)
    }

    actual fun getTestKey(): String? {
        return readPreferences().testKey
    }

}