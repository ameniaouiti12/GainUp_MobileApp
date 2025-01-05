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
import tn.esprit.gainupdam.BottomNavigationBar.BottomNavigation
import tn.esprit.gainupdam.Exercise
import tn.esprit.gainupdam.RetrofitInstance

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkoutDetailsScreen(navController: NavHostController, id: String) {
    var exercise by remember { mutableStateOf<Exercise?>(null) }

    LaunchedEffect(id) {
        RetrofitInstance.api.getExerciseById(id).enqueue(object : Callback<Exercise> {
            override fun onResponse(call: Call<Exercise>, response: Response<Exercise>) {
                if (response.isSuccessful) {
                    exercise = response.body()
                }
            }

            override fun onFailure(call: Call<Exercise>, t: Throwable) {
                // Handle failure
            }
        })
    }

    exercise?.let {
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
                        text = "Workout Details",
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

                // Workout title
                Text(
                    text = it.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Workout description
                Text(
                    text = it.description,
                    color = Color.White,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Workout details
                Text(
                    text = "Duration: ${it.duration} min",
                    color = Color.White,
                    fontSize = 16.sp
                )
                Text(
                    text = "Calories: ${it.calories} cal",
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
