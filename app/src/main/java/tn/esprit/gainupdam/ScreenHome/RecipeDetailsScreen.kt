package tn.esprit.gainupdam.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import tn.esprit.gainupdam.Meal

@Composable
fun MealDetailsScreen(navController: NavHostController, mealId: String) {
    // Suppose you have a function to get meal details by ID
    val meal = getMealById(mealId)

    meal?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberImagePainter(data = it.imageUrl),
                contentDescription = it.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = it.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Calories: ${it.calories}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Dummy function to get meal by ID
fun getMealById(mealId: String): Meal? {
    // Replace this with your actual implementation
    return Meal(
        _id = mealId,
        name = "Grilled Salmon",
        description = "Delicious grilled salmon with lemon and herbs.",
        imageUrl = "https://example.com/grilled_salmon.jpg",
        calories = 350
    )
}
