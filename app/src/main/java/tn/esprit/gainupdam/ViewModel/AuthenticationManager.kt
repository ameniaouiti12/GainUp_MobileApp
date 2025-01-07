package tn.esprit.gainupdam.ViewModel

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class AuthenticationManager(private val context: Context) {

    private val sharedPreferences by lazy {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        EncryptedSharedPreferences.create(
            "user_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private val _isAuthenticated = MutableLiveData(false)
    val isAuthenticated: LiveData<Boolean> get() = _isAuthenticated

    fun checkIfAuthenticated() {
        _isAuthenticated.value = sharedPreferences.getBoolean("is_authenticated", false)
    }

    fun signIn(username: String, password: String): Boolean {
        // Simuler une API REST (remplacer par un appel réseau)
        if (username == "john.doe" && password == "password123") {
            sharedPreferences.edit {
                putBoolean("is_authenticated", true)
                putString("username", username)
            }
            _isAuthenticated.value = true
            return true
        }
        return false
    }

    fun signOut() {
        sharedPreferences.edit {
            remove("is_authenticated")
            remove("username")
        }
        _isAuthenticated.value = false
    }

    fun getUserId(): String {
        return sharedPreferences.getString("userId", "") ?: ""
    }

    fun saveUserId(userId: String) {
        sharedPreferences.edit {
            putString("userId", userId)
        }
    }
}
