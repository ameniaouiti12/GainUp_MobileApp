package tn.esprit.projectgainup.remote
// ApiService.kt
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import tn.esprit.gainupdam.dtos.AuthResponse
import tn.esprit.gainupdam.dtos.ChangePasswordRequest
import tn.esprit.gainupdam.dtos.ForgotPasswordRequest
import tn.esprit.gainupdam.dtos.GenericResponse
import tn.esprit.gainupdam.dtos.LoginRequest
import tn.esprit.gainupdam.dtos.ResetPasswordRequest
import tn.esprit.gainupdam.dtos.SignupRequest
import tn.esprit.gainupdam.dtos.VerifyOtpRequest

interface ApiService {
    @POST("auth/signup")
    fun signUp(@Body signupData: SignupRequest): Call<AuthResponse>

    @POST("auth/login")
    fun login(@Body credentials: LoginRequest): Call<AuthResponse>

    @POST("auth/forgot-password")
    fun forgotPassword(@Body email: ForgotPasswordRequest): Call<GenericResponse>

    @POST("auth/verify-otp")
    fun verifyOtp(@Body verifyOtpRequest: VerifyOtpRequest): Call<GenericResponse>


    @PUT("auth/reset-password")
    fun resetPassword(@Body resetData: ResetPasswordRequest): Call<GenericResponse>

    @PUT("auth/change-password")
    fun changePassword(@Body changePasswordData: ChangePasswordRequest): Call<GenericResponse>
    @GET("auth/check-email")
    fun checkEmailExists(@Query("email") email: String): Call<GenericResponse>

}
