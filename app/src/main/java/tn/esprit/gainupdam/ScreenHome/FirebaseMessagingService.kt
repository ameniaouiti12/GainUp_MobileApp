package tn.esprit.gainupdam.java

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import tn.esprit.gainupdam.MainActivity
import tn.esprit.gainupdam.R
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications

// Rename to CustomNotification to avoid conflict
data class CustomNotification(val message: String, val time: String, val icon: ImageVector)

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            val title = remoteMessage.notification?.title ?: "Notification"
            val message = remoteMessage.notification?.body ?: ""
            val icon = Icons.Default.Notifications
            val time = "Now"
            val userId = remoteMessage.data["userId"] ?: "defaultUser"

            // Call addNotification after converting the custom notification
            val notification = createNotification(title, message)
            NotificationsManager.addNotification(applicationContext, userId, notification)
            showNotification(title, message)
        }
    }

    // Convert CustomNotification to android.app.Notification
    private fun createNotification(title: String?, message: String?): android.app.Notification {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // Use a resource ID for the icon (you may replace with your own icon)
        val iconResource = R.drawable.ic_notif

        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(iconResource)  // Use a drawable resource
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .build()
    }

    // Notification display logic
    @SuppressLint("ServiceCast")
    private fun showNotification(title: String?, message: String?) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, createNotification(title, message))
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        // Implement this method to send the token to your server
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}

// Implement the NotificationsManager
object NotificationsManager {

    // Add a notification to the manager (can save or handle it as needed)
    fun addNotification(context: Context, userId: String, notification: android.app.Notification) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(userId.hashCode(), notification)
    }
}
