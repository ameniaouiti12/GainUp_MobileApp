package tn.esprit.gainupdam.ScreenHome

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import tn.esprit.gainupdam.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController) {
    var messageText by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            Message(
                text = "Hello, I'm FitBot! 👋 I'm your personal sport assistant. How can I help you?",
                isFromBot = true
            )
        )
    }

    val apiChatScreen = ApiChatScreen() // Instance de l'API

    // Scroll state pour permettre le défilement jusqu'au bas après l'ajout d'un message
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        // Défile automatiquement vers le dernier élément de la liste
        listState.animateScrollToItem(messages.size - 1)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF03224c))
    ) {
        // Barre supérieure
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Surface(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        color = Color.Gray
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.coach),
                            contentDescription = "Coach Avatar",
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "FitBot Coach",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(Color.Green)
                            ) {}
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Always Active",
                                color = Color.LightGray,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigate("home") }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF03224c)
            )
        )
        // Liste des messages
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            state = listState
        ) {
            items(messages) { message ->
                ChatMessage(message)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Message Input
        MessageInput(
            value = messageText,
            onValueChange = { messageText = it },
            onSendClick = {
                if (messageText.isNotEmpty()) {
                    // Ajouter le message de l'utilisateur
                    messages.add(Message(messageText, isFromBot = false))

                    // Obtenir la réponse du bot
                    apiChatScreen.getResponse(messageText) { responseText ->
                        messages.add(Message(responseText, isFromBot = true))
                    }
                    messageText = ""
                }
            }
        )
    }
}

data class Message(
    val text: String,
    val isFromBot: Boolean
)

@Composable
fun ChatMessage(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (message.isFromBot) Arrangement.Start else Arrangement.End
    ) {
        if (message.isFromBot) {
            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                color = Color.Gray
            ) {
                Image(
                    painter = painterResource(id = R.drawable.coach),
                    contentDescription = "Coach Avatar",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (message.isFromBot) Color.White else Color(0xFF2196F3),
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(12.dp),
                color = if (message.isFromBot) Color.Black else Color.White
            )
        }

        if (!message.isFromBot) {
            Spacer(modifier = Modifier.width(8.dp))
            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                color = Color.Gray
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user_avatar),
                    contentDescription = "User Avatar",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun MessageInput(
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit // Remplacer @Composable () -> Unit par () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(24.dp)),
                placeholder = { Text("Type a message...") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.LightGray.copy(alpha = 0.1f),
                    focusedContainerColor = Color.LightGray.copy(alpha = 0.1f)
                ),
                singleLine = true
            )

            IconButton(
                onClick = { /* Handle mic click */ }, // Action pour le clic du micro
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Voice Input",
                    tint = Color(0xFF2196F3)
                )
            }

            IconButton(
                onClick = onSendClick, // Appeler la fonction passée en paramètre
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = Color(0xFF2196F3)
                )
            }
        }
    }
}
