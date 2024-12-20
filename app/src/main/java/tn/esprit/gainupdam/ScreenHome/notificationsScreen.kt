package tn.esprit.gainupdam.ScreenHome

import android.content.Context
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import tn.esprit.gainupdam.module.CustomNotification
import tn.esprit.gainupdam.utils.NotificationsManager
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(userId: String, context: Context) {
    val notifications = remember { mutableStateListOf<CustomNotification>() }
    LaunchedEffect(userId) {
        notifications.clear()
        notifications.addAll(NotificationsManager.getNotifications(context, userId))

        // Add static notifications
        notifications.add(
            CustomNotification(
                message = "Il est temps de boire de l'eau! Restez hydraté pour une meilleure santé.",
                time = "11:03",
                icon = Icons.Default.LocalDrink
            )
        )
        notifications.add(
            CustomNotification(
                message = "Votre séance d'entraînement commence dans 15 minutes. Soyez prêt!",
                time = "Now",
                icon = Icons.Default.FitnessCenter
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .background(Color(0xFF03224c)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Notifications",
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        notifications.forEach { notification ->
            var isDismissed by remember { mutableStateOf(false) }

            if (!isDismissed) {
                SwipeToDismissBox(
                    modifier = Modifier.padding(vertical = 4.dp),
                    backgroundContent = {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(Color(0xFF2196F3))
                                .padding(horizontal = 20.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.White
                            )
                        }
                    },
                    onDismissed = {
                        NotificationsManager.removeNotification(context, userId, notification)
                        isDismissed = true
                    },
                    content = {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF1E1E1E))
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF1E1E1E)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = notification.icon,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Text(
                                        text = notification.message,
                                        fontSize = 16.sp,
                                        color = Color.White
                                    )
                                    Text(
                                        text = notification.time,
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SwipeToDismissBox(
    modifier: Modifier = Modifier,
    backgroundContent: @Composable () -> Unit,
    onDismissed: () -> Unit,
    content: @Composable () -> Unit,
) {
    var offsetX by remember { mutableStateOf(0f) } // Track horizontal drag offset
    var dismissed by remember { mutableStateOf(false) } // Track dismiss state

    // Animate offset back to 0 when swipe is incomplete
    val animatedOffsetX by animateFloatAsState(
        targetValue = if (dismissed) -500f else offsetX,
        label = "SwipeOffsetAnimation"
    )

    Box(modifier = modifier) {
        // Background content (e.g., delete icon and blue background)
        Box(Modifier.matchParentSize()) {
            backgroundContent()
        }

        // Foreground content (the actual notification card)
        Box(
            modifier = Modifier
                .offset(x = animatedOffsetX.dp) // Apply swipe offset
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            // If drag exceeds threshold, trigger dismiss
                            if (abs(offsetX) > 150) {
                                dismissed = true
                                onDismissed()
                            } else {
                                offsetX = 0f // Reset offset
                            }
                        },
                        onHorizontalDrag = { _, dragAmount ->
                            offsetX += dragAmount
                        }
                    )
                }
        ) {
            content()
        }
    }
}
