package tn.esprit.gainupdam.ScreensUserMangement

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.navigation.NavController
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
fun facebookSignUp(navController: NavController, callbackManager: CallbackManager, context: ComponentActivity) {
    val loginManager = LoginManager.getInstance()
    loginManager.logInWithReadPermissions(context, listOf("email", "public_profile"))
    loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
        override fun onCancel() {
            Toast.makeText(context.applicationContext, "Connexion annulée", Toast.LENGTH_SHORT).show()
        }

        override fun onError(error: FacebookException) {
            Toast.makeText(context.applicationContext, "Erreur d'authentification Facebook", Toast.LENGTH_SHORT).show()
        }

        override fun onSuccess(result: LoginResult) {
            val accessToken = result.accessToken
            getFacebookUserInfo(accessToken, navController, context)
        }
    })
}

private fun getFacebookUserInfo(accessToken: AccessToken, navController: NavController, context: ComponentActivity) {
    val request = GraphRequest.newMeRequest(accessToken) { `object`, _ ->
        try {
            val name = `object`?.getString("name")
            val email = `object`?.getString("email")
            handleUserInfo(name, email, navController, context)
        } catch (e: Exception) {
            Toast.makeText(context.applicationContext, "Erreur lors de la récupération des informations", Toast.LENGTH_SHORT).show()
        }
    }
    val parameters = Bundle()
    parameters.putString("fields", "id,name,email")
    request.parameters = parameters
    request.executeAsync()
}

private fun handleUserInfo(name: String?, email: String?, navController: NavController, context: ComponentActivity) {
    if (!name.isNullOrEmpty() && !email.isNullOrEmpty()) {
        Toast.makeText(context.applicationContext, "Bienvenue $name", Toast.LENGTH_SHORT).show()
        // Naviguer vers l'écran d'accueil après une connexion réussie
        navController.navigate("home") // Remplacez "home" par le bon écran
    } else {
        Toast.makeText(context.applicationContext, "Informations Facebook invalides", Toast.LENGTH_SHORT).show()
    }
}
