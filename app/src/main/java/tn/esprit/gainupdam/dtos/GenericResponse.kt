package tn.esprit.gainupdam.dtos

// GenericResponse.kt
data class GenericResponse(
    val success: Boolean?,
    val userId: String?,
    val message: String? = null,
    val exists: Boolean? = null
)



