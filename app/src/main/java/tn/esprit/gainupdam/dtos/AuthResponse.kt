package tn.esprit.gainupdam.dtos

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val token: String,
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)

