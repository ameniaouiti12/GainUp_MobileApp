package tn.esprit.gainupdam.ScreenHome

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import tn.esprit.gainupdam.BottomNavigationBar.BottomNavigation

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecipeDetailsScreen(
    navController: NavHostController,
    nutritionId: String
) {
    var nutrition by remember { mutableStateOf<Nutrition?>(null) }

    LaunchedEffect(Unit) {
        val call = ApiClient.nutritionApi.getNutritionById(nutritionId)
        call.enqueue(object : Callback<Nutrition> {
            override fun onResponse(call: Call<Nutrition>, response: Response<Nutrition>) {
                if (response.isSuccessful) {
                    nutrition = response.body()
                }
            }

            override fun onFailure(call: Call<Nutrition>, t: Throwable) {
                // Handle failure
            }
        })
    }

    nutrition?.let {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF03224c)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Row for the back button and title
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Back button
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    // Title
                    Text(
                        text = "Recipe Details",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Image
                Image(
                    painter = rememberImagePainter(data = it.imageUrl),
                    contentDescription = it.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Recipe title
                Text(
                    text = it.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Recipe description
                Text(
                    text = it.description,
                    color = Color.White,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.weight(1f))

                // Bottom navigation
                BottomNavigation(navController = navController)
            }
        }
    }
}
