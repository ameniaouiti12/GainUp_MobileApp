package tn.esprit.gainupdam.ViewModel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import tn.esprit.gainupdam.BottomNavigationBar.BottomNavigation
import tn.esprit.gainupdam.R

@Composable
fun HelpSupportScreen(navController: NavHostController) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF03224c))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image en haut de la page
            Image(
                painter = painterResource(id = R.drawable.helpsupport),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 24.dp)
            )

            Text(
                text = "Help & Support",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // FAQ Section
            Text(
                text = "Frequently Asked Questions",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Example FAQ
            FAQItem(question = "How do I reset my password?", answer = "You can reset your password by clicking on the 'Forgot Password' link on the login screen.")
            FAQItem(question = "How do I contact support?", answer = "You can contact our support team at support@gain-up.com.")

            Spacer(modifier = Modifier.height(24.dp))

            // Contact Support Section
            Text(
                text = "Contact Support",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Example Contact Information
            Text(
                text = "Email: support@gain-up.com",
                fontSize = 16.sp,
                color = Color(0xFF2196F3),
                modifier = Modifier
                    .clickable {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:support@gain-up.com")
                        }
                        context.startActivity(intent)
                    }
                    .padding(bottom = 8.dp)
            )
            Text(
                text = "Phone: +216 27 319 366",
                fontSize = 16.sp,
                color = Color(0xFF2196F3),
                modifier = Modifier
                    .clickable {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:+216319366")
                        }
                        context.startActivity(intent)
                    }
                    .padding(bottom = 8.dp)
            )
        }

        // Bottom Navigation Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            BottomNavigation(navController)
        }
    }
}

@Composable
fun FAQItem(question: String, answer: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0a1728), RoundedCornerShape(8.dp))
            .padding(16.dp)
            .padding(bottom = 8.dp)
    ) {
        Text(
            text = question,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = answer,
            fontSize = 14.sp,
            color = Color.White
        )
    }
}
