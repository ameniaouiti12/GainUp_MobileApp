package tn.esprit.gainupdam.utils

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.system.Os.remove
import android.util.Log

object SharedPreferencesUtils {


    private const val PREFS_NAME = "app_prefs"

    // Keys for user data and token
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_EMAIL = "user_email"
    private const val KEY_TOKEN = "token_key"
    private const val KEY_NAME = "user_name"

    private const val KEY_REMEMBER_ME = "remember_me"
    private const val KEY_PASSWORD = "password"
    private const val KEY_PROFILE_IMAGE_URI = "profile_image_uri"




    fun saveProfileImageUri(context: Context, uri: Uri?) {
        getSharedPreferences(context).edit().apply {
            putString(KEY_PROFILE_IMAGE_URI, uri?.toString())
            apply()
        }
    }

    fun getProfileImageUri(context: Context): Uri? {
        val uriString = getSharedPreferences(context).getString(KEY_PROFILE_IMAGE_URI, null)
        return if (!uriString.isNullOrEmpty()) {
            try {
                Uri.parse(uriString)
            } catch (e: Exception) {
                Log.e("SharedPreferencesUtils", "Invalid URI: $uriString", e)
                null
            }
        } else {
            null
        }
    }

    fun clearProfileImageUri(context: Context) {
        getSharedPreferences(context).edit().apply {
            remove(KEY_PROFILE_IMAGE_URI)
            apply()
        }
    }

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

    // Save remember me status
    fun saveRememberMe(context: Context, rememberMe: Boolean) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putBoolean(KEY_REMEMBER_ME, rememberMe).apply()
    }

    // Retrieve remember me status
    fun getRememberMe(context: Context): Boolean {
        val prefs = getSharedPreferences(context)
        return prefs.getBoolean(KEY_REMEMBER_ME, false)
    }

    // Save password
    fun savePassword(context: Context, password: String) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putString(KEY_PASSWORD, password).apply()
    }

    // Retrieve password
    fun getPassword(context: Context): String? {
        val prefs = getSharedPreferences(context)
        return prefs.getString(KEY_PASSWORD, null)
    }

    fun saveUserName(context: Context, fullName: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(KEY_NAME, fullName).apply()
    }

    fun getUserName(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_NAME, null)
    }
    // Clear user credentials
    fun clearCredentials(context: Context) {
        val prefs = getSharedPreferences(context)
        val editor = prefs.edit()
        editor.remove(KEY_USER_EMAIL)
        editor.remove(KEY_PASSWORD)
        editor.remove(KEY_REMEMBER_ME)
        editor.remove(KEY_PROFILE_IMAGE_URI) // Utilisez editor.remove ici
        editor.apply()
    }

}
