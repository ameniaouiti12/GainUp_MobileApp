package tn.esprit.gainupdam.Navigation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.gainupdam.ApiClient
import tn.esprit.gainupdam.Nutrition

@Composable
fun MealsList(navController: NavHostController, selectedDay: String) {
    var nutritionList by remember { mutableStateOf(emptyList<Nutrition>()) }

    LaunchedEffect(selectedDay) {
        val call = ApiClient.nutritionApi.getNutritionByDay(selectedDay)
        call.enqueue(object : Callback<List<Nutrition>> {
            override fun onResponse(call: Call<List<Nutrition>>, response: Response<List<Nutrition>>) {
                if (response.isSuccessful) {
                    nutritionList = response.body() ?: emptyList()
                }
            }

            override fun onFailure(call: Call<List<Nutrition>>, t: Throwable) {
                // Handle failure
            }
        })
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(nutritionList) { nutrition ->
            MealItem(
                title = nutrition.name,
                minutes = nutrition.calories.toString(),
                calories = nutrition.calories.toString(),
                imageUrl = nutrition.imageUrl,
                navController = navController,
                nutritionId = nutrition._id
            )
        }
    }
}

@Composable
private fun MealItem(
    title: String,
    minutes: String,
    calories: String,
    imageUrl: String,
    navController: NavHostController,
    nutritionId: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("recipe_detail/$nutritionId")
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Set the card background color to white
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = title,
                    color = Color.Black, // Set the text color to black
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = "Time",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "$minutes Minutes",
                        color = Color.Black, // Set the text color to black
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = "Calories",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "$calories Cal",
                        color = Color.Black, // Set the text color to black
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}
