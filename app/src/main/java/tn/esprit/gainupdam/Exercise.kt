package tn.esprit.gainupdam

data class User(
    val _id: String,
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val roleId: String,
    val workoutPlan: List<WorkoutPlanData>,
    val dietPlan: List<DietPlanData>
)

data class WorkoutPlanData(
    val day: String,
    val exercises: List<Exercise>
)

data class DietPlanData(
    val day: String,
    val meals: List<Meal>
)

data class Exercise(
    val _id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val duration: Int,
    val calories: Int
)

data class Meal(
    val name: String,
    val description: String,
    val imageUrl: String,
    val calories: Int
)
