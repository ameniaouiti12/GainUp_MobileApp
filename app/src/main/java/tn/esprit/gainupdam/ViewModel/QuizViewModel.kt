package tn.esprit.gainupdam.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizViewModel(private val context: Context) : ViewModel() {
    private val _age = MutableStateFlow(25)
    val age: StateFlow<Int> = _age

    private val _gender = MutableStateFlow("female")
    val gender: StateFlow<String> = _gender

    private val _height = MutableStateFlow(172)
    val height: StateFlow<Int> = _height

    private val _weight = MutableStateFlow(81)
    val weight: StateFlow<Int> = _weight

    private val _goal = MutableStateFlow("Cutting")
    val goal: StateFlow<String> = _goal

    private val _lifestyle = MutableStateFlow("normal")
    val lifestyle: StateFlow<String> = _lifestyle

    private val sharedPreferences = context.getSharedPreferences("QuizPreferences", Context.MODE_PRIVATE)

    init {
        loadPreferences()
    }

    private fun loadPreferences() {
        _age.value = sharedPreferences.getInt("age", 25)
        _gender.value = sharedPreferences.getString("gender", "female") ?: "female"
        _height.value = sharedPreferences.getInt("height", 172)
        _weight.value = sharedPreferences.getInt("weight", 81)
        _goal.value = sharedPreferences.getString("goal", "Cutting") ?: "Cutting"
        _lifestyle.value = sharedPreferences.getString("lifestyle", "normal") ?: "normal"
    }

    private fun savePreferences() {
        with(sharedPreferences.edit()) {
            putInt("age", _age.value)
            putString("gender", _gender.value)
            putInt("height", _height.value)
            putInt("weight", _weight.value)
            putString("goal", _goal.value)
            putString("lifestyle", _lifestyle.value)
            apply()
        }
    }

    fun calculateCalories(): Int {
        val age = 25
        val gender = "female"
        val height = 172
        val weight = 81
        val goal = "Cutting"
        val lifestyle = "normal"

        Log.d("QuizViewModel", "Age: $age, Gender: $gender, Height: $height, Weight: $weight, Goal: $goal, Lifestyle: $lifestyle")

        return when (gender) {
            "Male" -> (10 * weight + 6.25 * height - 5 * age + 5) * when (lifestyle) {
                "Très passif" -> 1.2
                "Passif" -> 1.375
                "Normal" -> 1.55
                "Actif" -> 1.725
                "Très actif" -> 1.9
                else -> 1.0
            }
            "Female" -> (10 * weight + 6.25 * height - 5 * age - 161) * when (lifestyle) {
                "Très passif" -> 1.2
                "Passif" -> 1.375
                "Normal" -> 1.55
                "Actif" -> 1.725
                "Très actif" -> 1.9
                else -> 1.0
            }
            else -> 0
        }.toInt()
    }
}
