package tn.esprit.gainupdam.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.gainupdam.dtos.AuthResponse
import tn.esprit.gainupdam.dtos.SignupRequest
import tn.esprit.gainupdam.utils.SharedPreferencesUtils
import tn.esprit.projectgainup.remote.RetrofitClient

class AuthViewModelSignUp(application: Application) : AndroidViewModel(application) {

    // État pour refléter le succès ou l'échec de l'inscription
    private val _signUpState = mutableStateOf(SignUpState())
    val signUpState: State<SignUpState> = _signUpState

    // État pour le chargement
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    // État pour contrôler le dialogue de succès
    private val _showSignUpDialog = mutableStateOf(false)
    val showSignUpDialog: State<Boolean> = _showSignUpDialog

    /**
     * Fonction principale d'inscription
     * @param fullName Nom de l'utilisateur
     * @param email Adresse email
     * @param password Mot de passe
     * @param confirmPassword Confirmation du mot de passe
     * @param callback Retour pour notifier l'UI du succès ou de l'échec
     */
    fun performSignUp(
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String,
        callback: (Boolean, String) -> Unit
    ) {
        // Validation des champs
        if (fullName.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(getApplication(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            callback(false, "Please fill all fields")
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplication(), "Invalid email format", Toast.LENGTH_SHORT).show()
            callback(false, "Invalid email format")
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(getApplication(), "Passwords do not match", Toast.LENGTH_SHORT).show()
            callback(false, "Passwords do not match")
            return
        }

        if (password.length < 6) {
            Toast.makeText(getApplication(), "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
            callback(false, "Password must be at least 6 characters long")
            return
        }

        // Construire la requête pour l'API
        val signUpRequest = SignupRequest(fullName, email, password, confirmPassword)

        // Indiquer que le chargement est en cours
        _isLoading.value = true

        // Appel à l'API via Retrofit
        RetrofitClient.apiService.signUp(signUpRequest).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                _isLoading.value = false
                if (response.isSuccessful && response.body()?.success == true) {
                    // Sauvegarder les données utilisateur
                    saveUserDataToPreferences(fullName, email, password)

                    // Mettre à jour l'état
                    _signUpState.value = SignUpState(success = true, message = "Sign-up successful!")
                    _showSignUpDialog.value = true
                    Toast.makeText(getApplication(), "Sign-up successful!", Toast.LENGTH_SHORT).show()
                    callback(false, "Sign-up successful!")
                } else {
                    val message = response.body()?.message ?: "Sign-up failed!"
                    _signUpState.value = SignUpState(success = false, message = message)
                    Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
                    callback(false, message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                _isLoading.value = false
                val errorMessage = "Network error: ${t.message}"
                _signUpState.value = SignUpState(success = false, message = errorMessage)
                Toast.makeText(getApplication(), errorMessage, Toast.LENGTH_SHORT).show()
                callback(false, errorMessage)
            }
        })
    }

    /**
     * Sauvegarder les données utilisateur dans les SharedPreferences
     */
    private fun saveUserDataToPreferences(fullName: String, email: String, password: String) {
        val context = getApplication<Application>().applicationContext
        SharedPreferencesUtils.saveUserName(context, fullName)
        SharedPreferencesUtils.saveUserEmail(context, email)
        SharedPreferencesUtils.savePassword(context, password)
    }

    /**
     * Masquer le dialogue de succès
     */
    fun dismissSignUpDialog() {
        _showSignUpDialog.value = false
    }
}

// Modèle pour représenter l'état d'inscription
data class SignUpState(
    val success: Boolean = false,
    val message: String = ""
)
