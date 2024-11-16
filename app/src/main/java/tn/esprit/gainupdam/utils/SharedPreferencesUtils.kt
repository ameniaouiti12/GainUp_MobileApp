package tn.esprit.gainupdam.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesUtils {

    private const val PREFS_NAME = "app_prefs"

    // Keys for user data and token
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_EMAIL = "user_email"
    private const val KEY_TOKEN = "token_key"

    // Get SharedPreferences instance
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Save user ID
    fun saveUserId(context: Context, userId: String) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putString(KEY_USER_ID, userId).apply()
    }

    // Retrieve user ID
    fun getUserId(context: Context): String? {
        val prefs = getSharedPreferences(context)
        return prefs.getString(KEY_USER_ID, null)
    }

    // Save user email
    fun saveUserEmail(context: Context, email: String) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putString(KEY_USER_EMAIL, email).apply()
    }

    // Retrieve user email
    fun getUserEmail(context: Context): String? {
        val prefs = getSharedPreferences(context)
        return prefs.getString(KEY_USER_EMAIL, null)
    }

    // Save token
    fun saveToken(context: Context, token: String) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    // Retrieve token
    fun getToken(context: Context): String? {
        val prefs = getSharedPreferences(context)
        return prefs.getString(KEY_TOKEN, null)
    }
}
