package tn.esprit.gainupdam.utils

import android.content.Context
import androidx.preference.PreferenceManager

object PreferencesHelper {
    private const val KEY_QUIZ_COMPLETED = "quiz_completed"

    fun isQuizCompleted(context: Context): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getBoolean(KEY_QUIZ_COMPLETED, false)
    }

    fun setQuizCompleted(context: Context, completed: Boolean) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit().putBoolean(KEY_QUIZ_COMPLETED, completed).apply()
    }
}