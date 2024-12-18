package tn.esprit.gainupdam

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NutritionApi {
    @GET("nutrition")
    fun getAllNutrition(): Call<List<Nutrition>>

    @GET("nutrition/{id}")
    fun getNutritionById(@Path("id") id: String): Call<Nutrition>
}
