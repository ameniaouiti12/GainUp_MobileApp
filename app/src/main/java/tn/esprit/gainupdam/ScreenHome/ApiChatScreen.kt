package tn.esprit.gainupdam.ScreenHome

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ApiChatScreen {

    private val client = OkHttpClient()

    // API call method
    fun getResponse(question: String, callback: (String) -> Unit) {
        val MISTRAL_API_KEY = "DeGGMCMIj98B2JtGjxOpRdGdbn3sT6uR" // Replace with your actual Mistral API key
        val url = "https://api.mistral.ai/v1/chat/completions"

        val jsonPayload = JSONObject().apply {
            put("model", "open-mistral-7b") // Use the open-mistral-7b model
            put("temperature", 0.7) // Adjust temperature for more precise responses
            put("top_p", 1)
            put("max_tokens", 500) // Set a reasonable max_tokens value
            put("stream", false)
            put("stop", "string")
            put("random_seed", 0)
            put("messages", JSONArray().put(
                JSONObject().apply {
                    put("role", "system")
                    put("content", "You are FitBot, a friendly and knowledgeable sports assistant. Provide accurate and helpful information related to sports and fitness.")
                }
            ).put(
                JSONObject().apply {
                    put("role", "user")
                    put("content", question)
                }
            ))
            put("response_format", JSONObject().apply {
                put("type", "text")
            })
            put("tools", JSONArray().put(
                JSONObject().apply {
                    put("type", "function")
                    put("function", JSONObject().apply {
                        put("name", "string")
                        put("description", "")
                        put("parameters", JSONObject())
                    })
                }
            ))
            put("tool_choice", "auto")
            put("presence_penalty", 0)
            put("frequency_penalty", 0)
            put("n", 1)
            put("safe_prompt", false)
        }

        val requestBody = jsonPayload.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $MISTRAL_API_KEY")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("API Error", "API request failed", e)
                callback("Error: API request failed")
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (it.isSuccessful) {
                        val body = it.body?.string()
                        Log.d("API Response", body ?: "Empty response")
                        try {
                            val jsonResponse = JSONObject(body!!)
                            val responseText = jsonResponse
                                .getJSONArray("choices")
                                .getJSONObject(0)
                                .getJSONObject("message")
                                .getString("content")
                            callback(responseText)
                        } catch (e: Exception) {
                            Log.e("API Error", "Error parsing JSON response", e)
                            callback("Error: Invalid response format")
                        }
                    } else {
                        Log.e("API Error", "API request failed with code ${it.code}")
                        callback("Error: ${it.message}")
                    }
                }
            }
        })
    }
}
