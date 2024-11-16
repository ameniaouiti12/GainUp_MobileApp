package tn.esprit.gainupdam.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.gainupdam.dtos.*
import tn.esprit.gainupdam.utils.SharedPreferencesUtils
import tn.esprit.projectgainup.remote.RetrofitClient

class AuthViewModelVerifyOtp(application: Application) : AndroidViewModel(application) {

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _forgotPasswordState = mutableStateOf(ForgotPasswordState())
    val forgotPasswordState: State<ForgotPasswordState> = _forgotPasswordState

    private val _verifyOtpState = mutableStateOf(VerifyOtpState())
    val verifyOtpState: State<VerifyOtpState> = _verifyOtpState

    fun login(email: String, password: String, rememberMe: Boolean, callback: (Boolean) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            _loginState.value = LoginState(error = "Please fill all fields")
            callback(false)
            return
        }

        val loginRequest = LoginRequest(email, password)

        RetrofitClient.apiService.login(loginRequest).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val authResponse = response.body()
                    if (authResponse != null && authResponse.accessToken.isNotEmpty()) {
                        SharedPreferencesUtils.saveToken(getApplication(), authResponse.accessToken)
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

        val request = ForgotPasswordRequest(email = email)

        RetrofitClient.apiService.forgotPassword(request).enqueue(object : Callback<GenericResponse> {
            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                if (response.isSuccessful) {
                    val genericResponse = response.body()
                    val userId = genericResponse?.userId
                    if (userId != null) {
                        SharedPreferencesUtils.saveUserId(getApplication(), userId)
                        _forgotPasswordState.value = ForgotPasswordState(message = "OTP sent to your email")
                        callback(true)
                    } else {
                        _forgotPasswordState.value = ForgotPasswordState(error = "Failed to send OTP")
                        callback(false)
                    }
                } else {
                    _forgotPasswordState.value = ForgotPasswordState(error = "Error: ${response.message()}")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                _forgotPasswordState.value = ForgotPasswordState(error = "Network error")
                callback(false)
            }
        })
    }

    fun verifyOtp(otp: String, callback: (Boolean) -> Unit) {
        if (otp.isEmpty()) {
            _verifyOtpState.value = VerifyOtpState(error = "Please enter the OTP")
            callback(false)
            return
        }

        val userId = SharedPreferencesUtils.getUserId(getApplication())
        val email = SharedPreferencesUtils.getUserEmail(getApplication())  // Retrieve email from SharedPreferences

        // Ajout de logs pour déboguer
        Log.d("VerifyOtp", "UserId: $userId, Email: $email")

        if (userId.isNullOrEmpty() || email.isNullOrEmpty()) {
            _verifyOtpState.value = VerifyOtpState(error = "User ID or Email not found")
            callback(false)
            return
        }

        // Pass email, userId, and otp to the request
        val request = VerifyOtpRequest(userId = userId, email = email, otp = otp)

        RetrofitClient.apiService.verifyOtp(request).enqueue(object : Callback<GenericResponse> {
            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                if (response.isSuccessful) {
                    val genericResponse = response.body()
                    if (genericResponse?.message == "OTP validated successfully. Please proceed to change your password.") {
                        _verifyOtpState.value = VerifyOtpState(message = "OTP validated successfully.")
                        callback(true)
                    } else {
                        _verifyOtpState.value = VerifyOtpState(error = "Invalid OTP")
                        callback(false)
                    }
                } else {
                    _verifyOtpState.value = VerifyOtpState(error = "Error: ${response.message()}")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                _verifyOtpState.value = VerifyOtpState(error = "Network error")
                callback(false)
            }
        })
    }



    data class LoginState(val error: String = "")
    data class ForgotPasswordState(val error: String = "", val message: String = "")
    data class VerifyOtpState(val error: String = "", val message: String = "")
}
