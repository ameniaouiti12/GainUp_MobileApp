package tn.esprit.gainupdam.dtos

// ResetPasswordRequest.kt
data class ResetPasswordRequest(
    val resetToken: String,
    val newPassword: String
)
