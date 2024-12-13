package tn.esprit.gainupdam

data class Exercise(
    val _id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val duration: Int,
    val calories: Int
)
