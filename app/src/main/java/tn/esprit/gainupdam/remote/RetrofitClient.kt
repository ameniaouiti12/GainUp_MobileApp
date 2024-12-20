package tn.esprit.projectgainup.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://192.168.1.10:3000/"

    // Remplacez par l'URL de votre API

    // Crée et configure Retrofit avec Gson
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Instance de ApiService pour les appels réseau
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
