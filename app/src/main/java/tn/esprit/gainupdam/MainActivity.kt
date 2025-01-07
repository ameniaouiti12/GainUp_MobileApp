package tn.esprit.gainupdam

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import tn.esprit.gainupdam.ScreenHome.ChatScreen
import tn.esprit.gainupdam.ScreenHome.EditProfileScreen
import tn.esprit.gainupdam.ScreenHome.HomeScreen
import tn.esprit.gainupdam.ScreenHome.NotificationsScreen
import tn.esprit.gainupdam.ScreenHome.NutritionScreen
import tn.esprit.gainupdam.ScreenHome.RecipeDetailsScreen
import tn.esprit.gainupdam.ScreensUserMangement.*
import tn.esprit.gainupdam.ViewModel.*
import tn.esprit.gainupdam.screens.AgeScreen
import tn.esprit.gainupdam.screens.CalorieScreen
import tn.esprit.gainupdam.screens.GenderScreen
import tn.esprit.gainupdam.screens.GoalScreen
import tn.esprit.gainupdam.screens.HeightScreen
import tn.esprit.gainupdam.screens.LifestyleScreen
import tn.esprit.gainupdam.screens.WeightScreen
import tn.esprit.gainupdam.utils.PreferencesHelper
import tn.esprit.gainupdam.utils.handleGoogleSignInSuccess

class MainActivity : ComponentActivity() {
    private lateinit var callbackManager: CallbackManager
    private val navigateToHomeLiveData = MutableLiveData<Boolean>()
    private val navigateToGenderLiveData = MutableLiveData<Boolean>()
    private lateinit var authManager: AuthenticationManager

    // Declare the permission launcher
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // Inform user that your app will not show notifications.
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(applicationContext)
        callbackManager = CallbackManager.Factory.create()
        authManager = AuthenticationManager(this)

        setContent {
            GainUpDamApp(callbackManager, this, navigateToHomeLiveData, navigateToGenderLiveData, authManager)
        }

        askNotificationPermission()

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d(TAG, msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(task, this)
        }
    }

    private fun handleGoogleSignInResult(task: Task<GoogleSignInAccount>, context: Context) {
        try {
            val account = task.getResult(ApiException::class.java)
            handleGoogleSignInSuccess(account, context)
        } catch (e: ApiException) {
            Toast.makeText(context, "Erreur de connexion Google: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleGoogleSignInSuccess(account: GoogleSignInAccount?, context: Context) {
        account?.let {
            if (PreferencesHelper.isQuizCompleted(context)) {
                navigateToHomeLiveData.value = true
            } else {
                navigateToGenderLiveData.value = true
            }
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // Display an educational UI explaining to the user the features that will be enabled
                // by them granting the POST_NOTIFICATION permission. This UI should provide the user
                // "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                // If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val RC_SIGN_IN = 9001
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ComposableDestinationInComposeScope")
@Composable
fun GainUpDamApp(
    callbackManager: CallbackManager,
    context: ComponentActivity,
    navigateToHomeLiveData: MutableLiveData<Boolean>,
    navigateToGenderLiveData: MutableLiveData<Boolean>,
    authManager: AuthenticationManager
) {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val authViewModelSignUp: AuthViewModelSignUp = viewModel()
    val authViewModelForgotPassword: AuthViewModelForgotPassword = viewModel()
    val authViewModelVerifyOtp: AuthViewModelVerifyOtp = viewModel()

    navigateToHomeLiveData.observe(context as LifecycleOwner) { shouldNavigate ->
        shouldNavigate?.let {
            if (it) {
                navController.navigate("home") {
                    popUpTo("sign_in") { inclusive = true }
                }
                navigateToHomeLiveData.value = false
            }
        }
    }

    navigateToGenderLiveData.observe(context as LifecycleOwner) { shouldNavigate ->
        shouldNavigate?.let {
            if (it) {
                navController.navigate("gender") {
                    popUpTo("sign_in") { inclusive = true }
                }
                navigateToGenderLiveData.value = false
            }
        }
    }
    navigateToGenderLiveData.observe(context as LifecycleOwner) { shouldNavigate ->
        shouldNavigate?.let {
            if (it) {
                navController.navigate("sign_in") {
                    popUpTo("sign_up") { inclusive = true }
                }
                navigateToGenderLiveData.value = false
            }
        }
    }


    NavHost(navController = navController, startDestination = "get_started") {
        composable("get_started") { GetStartedScreen(navController) }
        composable("sign_in") {
            SignInScreen(
                navController = navController,
                authViewModel = authViewModel,
                callbackManager = callbackManager,
                context = context
            )
        }
        composable("sign_up") {
            SignUpScreen(
                navController = navController,
                authViewModelSignUp = authViewModelSignUp,
                callbackManager = callbackManager,
                context = context
            )
        }
        composable("forgot_password") {
            ForgotPasswordScreen(
                navController,
                authViewModelForgotPassword
            )
        }
        composable("change_password") { ChangePasswordScreen(navController) }
        composable("home") {  
          backStackEntry ->
            val calories = backStackEntry.arguments?.getString("calories")?.toIntOrNull() ?: 0
          HomeScreen(navController, authManager)}
        composable("profile") { ProfileScreen(navController) }
        composable("editProfileScreen") { EditProfileScreen(navController) }
        composable("verify_otp") { VerifyOtpScreen(navController, authViewModelVerifyOtp, "") }
        composable("messages") { ChatScreen(navController) }
        composable("diet") {
            var selectedDay by remember { mutableStateOf("Tuesday") }
            DietScreen(navController, selectedDay, { day ->
                selectedDay = day
            }, authManager)
        }
        composable(
            "meal_detail/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("id") ?: ""
            MealDetailsScreen(navController, mealId)
        }
        composable("workout") {
            var selectedDay by remember { mutableStateOf("Tuesday") }
            WorkoutScreen(navController, selectedDay, { day ->
                selectedDay = day
            }, authManager)
        }
        composable(
            "workout_detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            WorkoutDetailsScreen(navController, id)

        }

        // Quiz Screens
        composable("gender") { GenderScreen(navController) }
        composable("age") { AgeScreen(navController) }
        composable("height") { HeightScreen(navController) }
        composable("weight") { WeightScreen(navController) }
        composable("goal") { GoalScreen(navController) }
        composable("lifestyle") { LifestyleScreen(navController) }
        composable("calorie") { CalorieScreen(navController) }

        // Profile and Notifications Screens
        composable("profileScreen") {
            ProfileScreen(navController)
        }
        composable("notificationsScreen/{userId}") { backStackEntry ->
            val userId = PreferencesHelper.getUserId(context) ?: ""
            NotificationsScreen(userId, context)
        }
        composable("helpSupport") {
            HelpSupportScreen(navController)
        }


    }
}