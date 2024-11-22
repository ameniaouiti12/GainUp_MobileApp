package tn.esprit.gainupdam

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.google.android.gms.auth.api.signin.GoogleSignIn
import tn.esprit.gainupdam.ScreenHome.ChatScreen
import tn.esprit.gainupdam.ScreenHome.EditProfileScreen
import tn.esprit.gainupdam.ScreenHome.HomeScreen
import tn.esprit.gainupdam.ScreensUserMangement.*
import tn.esprit.gainupdam.ViewModel.*

class MainActivity : ComponentActivity() {
    private lateinit var callbackManager: CallbackManager
    private val navigateToHomeLiveData = MutableLiveData<Boolean>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(applicationContext)
        callbackManager = CallbackManager.Factory.create()

        setContent {
            GainUpDamApp(callbackManager, this, navigateToHomeLiveData)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.result
            if (account != null) {
                navigateToHomeLiveData.value = true
            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ComposableDestinationInComposeScope")
@Composable
fun GainUpDamApp(
    callbackManager: CallbackManager,
    context: ComponentActivity,
    navigateToHomeLiveData: MutableLiveData<Boolean>
) {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val authViewModelSignUp: AuthViewModelSinUp = viewModel()
    val authViewModelForgotPassword: AuthViewModelForgotPassword = viewModel()
    val authViewModelVerifyOtp: AuthViewModelVerifyOtp = viewModel()
    val authManager = AuthenticationManager(context)
    navigateToHomeLiveData.observe(context as LifecycleOwner) { shouldNavigate ->
        if (shouldNavigate == true) {
            navController.navigate("home") {
                popUpTo("sign_in") { inclusive = true }
            }
            navigateToHomeLiveData.value = false
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
        composable("home") { HomeScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("editProfileScreen") { EditProfileScreen(navController) }
        composable("verify_otp") { VerifyOtpScreen(navController, authViewModelVerifyOtp, "") }
        composable("messages") { ChatScreen(navController) }
    }
}