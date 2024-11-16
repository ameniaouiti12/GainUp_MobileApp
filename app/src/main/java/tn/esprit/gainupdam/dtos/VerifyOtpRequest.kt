package tn.esprit.gainupdam.dtos

data class VerifyOtpRequest(
    val userId: String,
    val email: String,  // Added email field
    val otp: String

)

