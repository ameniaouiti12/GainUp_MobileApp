package tn.esprit.gainupdam.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tn.esprit.gainupdam.module.CustomNotification

object NotificationsManager {
    private const val PREFS_NAME = "notifications_prefs"
    private const val NOTIFICATIONS_KEY = "notifications"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun addNotification(context: Context, userId: String, notification: CustomNotification) {
        val notifications = getNotifications(context, userId).toMutableList()
        notifications.add(notification)
        saveNotifications(context, userId, notifications)
    }

    fun removeNotification(context: Context, userId: String, notification: CustomNotification) {
        val notifications = getNotifications(context, userId).toMutableList()
        notifications.remove(notification)
        saveNotifications(context, userId, notifications)
    }

    fun getNotifications(context: Context, userId: String): List<CustomNotification> {
        val prefs = getSharedPreferences(context)
        val json = prefs.getString("${NOTIFICATIONS_KEY}_$userId", null)
        return if (json != null) {
            Gson().fromJson(json, object : TypeToken<List<CustomNotification>>() {}.type)
        } else {
            emptyList()
        }
    }

    private fun saveNotifications(context: Context, userId: String, notifications: List<CustomNotification>) {
        val prefs = getSharedPreferences(context)
        val editor = prefs.edit()
        val json = Gson().toJson(notifications)
        editor.putString("${NOTIFICATIONS_KEY}_$userId", json)
        editor.apply()
    }
}
