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
import tn.esprit.gainupdam.dtos.LoginRequest
import tn.esprit.gainupdam.utils.SharedPreferencesUtils
import tn.esprit.projectgainup.remote.RetrofitClient

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

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
                    Log.d("AuthViewModel", "AuthResponse: $authResponse")
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
}

data class LoginState(val error: String = "")
