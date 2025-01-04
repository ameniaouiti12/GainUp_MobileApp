package tn.esprit.gainupdam

data class WorkoutPlanResponse(
    val success: Boolean,
    val data: WorkoutPlanData
)

data class WorkoutPlanData(
    val day: String,
    val exercises: List<Exercise>
)

data class Exercise(
    val _id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val duration: Int,
    val calories: Int
)
