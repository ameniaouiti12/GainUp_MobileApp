package tn.esprit.gainupdam.ViewModel
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.core.content.edit

class AuthenticationManager(private val context: Context) {

    // Variable pour suivre l'état de l'utilisateur
    var isAuthenticated by mutableStateOf(false)
        private set

    // Méthode pour vérifier si l'utilisateur est connecté
    fun checkIfAuthenticated() {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        isAuthenticated = sharedPreferences.getBoolean("is_authenticated", false)
    }

    // Méthode pour se connecter (simulation)
    fun signIn(username: String, password: String): Boolean {
        // Simuler un appel de connexion ici (par exemple, une API)
        if (username == "john.doe" && password == "password123") {
            // Sauvegarder l'état de connexion dans SharedPreferences
            val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            sharedPreferences.edit {
                putBoolean("is_authenticated", true)
            }
            isAuthenticated = true
            return true
        }
        return false
    }

    // Méthode pour se déconnecter
    fun signOut() {
        // Effacer l'état de connexion dans SharedPreferences
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit {
            remove("is_authenticated")
        }
        isAuthenticated = false
    }

    // Méthode pour récupérer le nom d'utilisateur
    fun getUsername(): String {
        return if (isAuthenticated) {
            "John Doe"
        } else {
            "Guest"
        }
    }

    // Méthode pour vérifier si l'utilisateur est connecté
    fun isUserAuthenticated(): Boolean {
        return isAuthenticated
    }
}
