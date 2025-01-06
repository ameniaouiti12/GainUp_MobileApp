package tn.esprit.gainupdam.ScreenHome

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.gainupdam.BottomNavigationBar.BottomNavigation
import tn.esprit.gainupdam.Navigation.TopBar
import tn.esprit.gainupdam.Navigation.MealList
import tn.esprit.gainupdam.RetrofitInstance
import tn.esprit.gainupdam.User
import tn.esprit.gainupdam.ViewModel.AuthenticationManager

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DietScreen(
    navController: NavHostController,
    day: String,
    onDaySelected: (String) -> Unit,
    authManager: AuthenticationManager
) {
    Log.d("DietScreen", "Day parameter: $day")

    var user by remember { mutableStateOf<User?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    val backgroundColor = Color(0xFF03224c)

    // Récupérer l'ID de l'utilisateur actuellement connecté
    val userId = authManager.getUserId()

    LaunchedEffect(userId) {
        RetrofitInstance.api.getUserById(userId).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    user = response.body()?.firstOrNull()
                } else {
                    isError = true
                    Log.e("DietScreen", "Error fetching user: ${response.errorBody()?.string()}")
                }
                isLoading = false
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                isError = true
                isLoading = false
                Log.e("DietScreen", "Network error: ${t.message}")
            }
        })
    }

    val filteredMeals = user?.dietPlan?.find { it.day == day }?.meals

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TopBar(navController, onDaySelected)
            Spacer(modifier = Modifier.height(16.dp))

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                isError -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Failed to load diet plans. Please try again.",
                            color = Color.Red,
                            fontSize = 16.sp
                        )
                    }
                }
                else -> {
                    filteredMeals?.let {
                        MealList(navController, it)
                    } ?: run {
                        Text("No diet plan available for $day")
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            BottomNavigation(navController)
        }
    }
}
