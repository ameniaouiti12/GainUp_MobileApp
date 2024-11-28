package tn.esprit.gainupdam.utils

import android.content.Context
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import tn.esprit.gainupdam.utils.PreferencesHelper

fun handleGoogleSignInSuccess(account: GoogleSignInAccount, navController: NavController, context: Context) {
    if (account != null) {
        if (PreferencesHelper.isQuizCompleted(context)) {
            navController.navigate("home")
        } else {
            navController.navigate("gender")
        }
    }
}
