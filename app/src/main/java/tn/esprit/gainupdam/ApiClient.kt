package tn.esprit.gainupdam

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://192.168.10.221:3000/" // Utilisez l'adresse IP de votre serveur local

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val nutritionApi: NutritionApi = retrofit.create(NutritionApi::class.java)
}
