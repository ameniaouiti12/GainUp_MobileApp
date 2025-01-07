package tn.esprit.gainupdam

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NutritionApi {
    @GET("nutrition")
    fun getAllNutrition(): Call<List<Nutrition>>

    @GET("nutrition/{id}")
    fun getNutritionById(@Path("id") id: String): Call<Nutrition>

    @GET("nutrition")
    fun getNutritionByDay(@Query("day") day: String): Call<List<Nutrition>>
}
