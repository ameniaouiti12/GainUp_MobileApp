package tn.esprit.gainupdam

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tn.esprit.gainupdam.ScreenHome.HomeScreen
import tn.esprit.gainupdam.ScreenProfile.ProfileScreen
import tn.esprit.gainupdam.ScreensUserMangement.ChangePasswordScreen
import tn.esprit.gainupdam.ScreensUserMangement.ForgotPasswordScreen
import tn.esprit.gainupdam.ScreensUserMangement.GetStartedScreen
import tn.esprit.gainupdam.ScreensUserMangement.SignInScreen
import tn.esprit.gainupdam.ScreensUserMangement.SignUpScreen
import tn.esprit.gainupdam.ScreensUserMangement.VerifyOtpScreen
import tn.esprit.gainupdam.ViewModel.AuthViewModel
import tn.esprit.gainupdam.ViewModel.AuthViewModelForgotPassword
import tn.esprit.gainupdam.ViewModel.AuthViewModelSinUp
import tn.esprit.gainupdam.ViewModel.AuthViewModelVerifyOtp

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GainUpDamApp()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ComposableDestinationInComposeScope")
@Composable
fun GainUpDamApp() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val authViewModelSignUp: AuthViewModelSinUp = viewModel()
    val authViewModelForgotPassword: AuthViewModelForgotPassword = viewModel()
    val authViewModelVerifyOtp: AuthViewModelVerifyOtp = viewModel()


    NavHost(navController = navController, startDestination = "get_started") {
        composable("get_started") { GetStartedScreen(navController) }
        composable("sign_in") { SignInScreen(navController, authViewModel) }
        composable("sign_up") { SignUpScreen(navController, authViewModelSignUp) }
        composable("forgot_password") { ForgotPasswordScreen(navController,authViewModelForgotPassword) }
        composable("change_password") { ChangePasswordScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("verify_otp") { VerifyOtpScreen(navController, authViewModelVerifyOtp,"") } // Pass the email as needed

    }
}
