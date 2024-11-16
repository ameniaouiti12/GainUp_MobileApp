package tn.esprit.gainupdam.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.gainupdam.dtos.AuthResponse
import tn.esprit.gainupdam.dtos.ForgotPasswordRequest
import tn.esprit.gainupdam.dtos.GenericResponse
import tn.esprit.gainupdam.dtos.LoginRequest
import tn.esprit.gainupdam.utils.SharedPreferencesUtils
import tn.esprit.projectgainup.remote.RetrofitClient

class AuthViewModelForgotPassword(application: Application) : AndroidViewModel(application) {

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _forgotPasswordState = mutableStateOf(ForgotPasswordState())
    val forgotPasswordState: State<ForgotPasswordState> = _forgotPasswordState

    fun login(email: String, password: String, rememberMe: Boolean, callback: (Boolean) -> Unit) {
        Log.d("AuthViewModel", "Login called with email: $email, password: $password, rememberMe: $rememberMe")

        if (email.isEmpty() || password.isEmpty()) {
            _loginState.value = LoginState(error = "Please fill all fields")
            callback(false)
            return
        }

        val loginRequest = LoginRequest(email, password)

        RetrofitClient.apiService.login(loginRequest).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("AuthViewModel", "Login response received")
                if (response.isSuccessful) {
                    val authResponse = response.body()
                    if (authResponse != null && authResponse.accessToken.isNotEmpty()) {
                        SharedPreferencesUtils.saveToken(context = getApplication(), token = authResponse.accessToken)
                        callback(true)
                    } else {
                        _loginState.value = LoginState(error = "Login failed: Invalid credentials")
                        callback(false)
                    }
                } else {
                    _loginState.value = LoginState(error = "Login failed: ${response.message()}")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.e("AuthViewModel", "Login failed: ${t.message}")
                _loginState.value = LoginState(error = "Network error")
                callback(false)
            }
        })
    }

    fun forgotPassword(email: String, callback: (Boolean) -> Unit) {
        if (email.isEmpty()) {
            _forgotPasswordState.value = ForgotPasswordState(error = "Please enter your email")
            callback(false)
            return
        }

        val request = ForgotPasswordRequest(email)

        RetrofitClient.apiService.forgotPassword(request).enqueue(object : Callback<GenericResponse> {
            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                Log.d("AuthViewModel", "Forgot password response received: ${response.code()}")

                // Vérifier le code de réponse
                if (response.isSuccessful) {
                    val genericResponse = response.body()
                    Log.d("AuthViewModel", "Response body: $genericResponse")

                    // Si la réponse est valide et contient un message de succès
                    if (genericResponse != null) {
                        Log.d("AuthViewModel", "Message: ${genericResponse.message}")

                        // L'OTP a été envoyé avec succès, afficher un message de succès
                        if (genericResponse.message != null && genericResponse.message.isNotEmpty()) {
                            _forgotPasswordState.value = ForgotPasswordState(message = "OTP sent to your email")
                            callback(true)
                        } else {
                            _forgotPasswordState.value = ForgotPasswordState(error = "Failed to send OTP")
                            callback(false)
                        }
                    } else {
                        Log.e("AuthViewModel", "Error: Response body is null.")
                        _forgotPasswordState.value = ForgotPasswordState(error = "Failed to send OTP. Empty response body.")
                        callback(false)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("AuthViewModel", "Error response: ${response.message()}, Error body: $errorBody")
                    _forgotPasswordState.value = ForgotPasswordState(error = "Error: ${response.message()}")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                Log.e("AuthViewModel", "Network error: ${t.message}")
                _forgotPasswordState.value = ForgotPasswordState(error = "Network error: ${t.message}")
                callback(false)
            }
        })
    }



    data class LoginState(val error: String = "")
    data class ForgotPasswordState(val error: String = "", val message: String = "")
}
