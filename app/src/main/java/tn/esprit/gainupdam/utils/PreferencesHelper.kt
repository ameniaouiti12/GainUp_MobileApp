package tn.esprit.gainupdam.utils

import android.content.Context
import android.preference.PreferenceManager

object PreferencesHelper {
    private const val KEY_QUIZ_COMPLETED = "quiz_completed"
    private const val PREFS_NAME = "user_prefs"
    private const val USER_ID_KEY = "user_id"

    fun saveUserId(context: Context, userId: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(USER_ID_KEY, userId).apply()
    }

    fun getUserId(context: Context): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(USER_ID_KEY, null)
    }
    fun isQuizCompleted(context: Context): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getBoolean(KEY_QUIZ_COMPLETED, false)
    }


    fun setQuizCompleted(context: Context, completed: Boolean) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit().putBoolean(KEY_QUIZ_COMPLETED, completed).apply()
    }
}