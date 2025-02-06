import com.example.myapplication.entity.UserPreferences
import platform.Foundation.NSUserDefaults
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

actual object UserManager {
//
//    private val userDefaults = NSUserDefaults.standardUserDefaults()
//
//    private const val PREFS_KEY = "user_preferences"
//
//    private fun readPreferences(): UserPreferences {
//        val preferencesJson = userDefaults.stringForKey(PREFS_KEY)
//        return if (preferencesJson != null) {
//            Json.decodeFromString(preferencesJson)
//        } else {
//            UserPreferences()
//        }
//    }
//
//    private fun writePreferences(preferences: UserPreferences) {
//        val preferencesJson = Json.encodeToString(preferences)
//        userDefaults.setObject(preferencesJson, PREFS_KEY)
//    }

    actual fun setTestKey(newTestKey: String) {
//        val preferences = readPreferences()
//        val updatedPreferences = preferences.copy(testKey = newTestKey)
//        writePreferences(updatedPreferences)
    }

    actual fun getTestKey(): String? {
//        return readPreferences().testKey
    }
}
