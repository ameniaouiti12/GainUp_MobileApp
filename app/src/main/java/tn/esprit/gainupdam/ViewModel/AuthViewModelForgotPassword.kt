package tn.esprit.gainupdam.ViewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
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
            Toast.makeText(getApplication(), "Please fill all fields", Toast.LENGTH_SHORT).show()
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
                        Toast.makeText(getApplication(), "Login failed: Invalid credentials", Toast.LENGTH_SHORT).show()
                        callback(false)
                    }
                } else {
                    Toast.makeText(getApplication(), "Login failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                    callback(false)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.e("AuthViewModel", "Login failed: ${t.message}")
                Toast.makeText(getApplication(), "Network error", Toast.LENGTH_SHORT).show()
                callback(false)
            }
        })
    }

    fun forgotPassword(email: String, callback: (Boolean) -> Unit) {
        if (email.isEmpty()) {
            Toast.makeText(getApplication(), "Please enter your email", Toast.LENGTH_SHORT).show()
            callback(false)
            return
        }

        val request = ForgotPasswordRequest(email)

        RetrofitClient.apiService.forgotPassword(request).enqueue(object : Callback<GenericResponse> {
            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                Log.d("AuthViewModel", "Forgot password response received: ${response.code()}")

                if (response.isSuccessful) {
                    val genericResponse = response.body()
                    Log.d("AuthViewModel", "Response body: $genericResponse")

                    if (genericResponse != null) {
                        Log.d("AuthViewModel", "Message: ${genericResponse.message}")

                        if (genericResponse.message != null && genericResponse.message.isNotEmpty()) {
                            Toast.makeText(getApplication(), "OTP sent to your email", Toast.LENGTH_SHORT).show()
                            callback(true)
                        } else {
                            Toast.makeText(getApplication(), "Failed to send OTP", Toast.LENGTH_SHORT).show()
                            callback(false)
                        }
                    } else {
                        Log.e("AuthViewModel", "Error: Response body is null.")
                        Toast.makeText(getApplication(), "Failed to send OTP. Empty response body.", Toast.LENGTH_SHORT).show()
                        callback(false)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("AuthViewModel", "Error response: ${response.message()}, Error body: $errorBody")
                    Toast.makeText(getApplication(), "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                    callback(false)
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                Log.e("AuthViewModel", "Network error: ${t.message}")
                Toast.makeText(getApplication(), "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                callback(false)
            }
        })
    }

    data class LoginState(val error: String = "")
    data class ForgotPasswordState(val error: String = "", val message: String = "")
}
