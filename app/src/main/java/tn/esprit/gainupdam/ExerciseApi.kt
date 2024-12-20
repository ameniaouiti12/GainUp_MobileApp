package tn.esprit.gainupdam

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ExerciseApi {
    @GET("exercises")
    fun getExercises(): Call<List<Exercise>>

    @GET("exercises/{id}")
    fun getExerciseById(@Path("id") id: String): Call<Exercise>

    @POST("exercises")
    fun createExercise(@Body exercise: Exercise): Call<Exercise>

    @PUT("exercises/{id}")
    fun updateExercise(@Path("id") id: String, @Body exercise: Exercise): Call<Exercise>

    @DELETE("exercises/{id}")
    fun deleteExercise(@Path("id") id: String): Call<Exercise>

    @GET("exercise/plan")
    fun getWorkoutPlanForDay(@Query("day") day: String): Call<WorkoutPlanResponse>
}
