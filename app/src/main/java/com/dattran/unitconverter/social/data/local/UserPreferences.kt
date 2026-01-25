package com.dattran.unitconverter.social.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// ⭐ Tạo DataStore instance
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences(private val context: Context) {
    companion object {
        // Keys
        private val ACCESS_TOKEN = stringPreferencesKey("auth_token")
        private val IS_AUTH = booleanPreferencesKey("is_auth")
    }

    // ⭐ Lấy token
    val accessToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[ACCESS_TOKEN]
        }

    // ⭐ Lấy isAuth
    val isAuth: Flow<Boolean?> = context.dataStore.data
        .map { preferences ->
            preferences[IS_AUTH]
        }

    // ⭐ Save login info (gọi khi login thành công)
    suspend fun saveLoginInfo(token: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
            preferences[IS_AUTH] = true
        }
    }

    // ⭐ Clear login info (gọi khi logout)
    suspend fun clearLoginInfo() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    // ⭐ Get token synchronously (cho API calls)
    suspend fun getToken(): String? {
        return context.dataStore.data.map { it[ACCESS_TOKEN] }.first()
    }
}