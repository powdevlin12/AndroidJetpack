package com.dattran.unitconverter.mvvm.repositories

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StoreImpl2 @Inject constructor(
    @ApplicationContext context: android.content.Context
) : Store {
    private val sharedPreferences =
        context.getSharedPreferences("MyPrefs", android.content.Context.MODE_PRIVATE)

    override fun getValue(key: String): String? {
        TODO("Not yet implemented")
        return sharedPreferences.getString(key, "") ?: ""
    }

    override fun setValue(key: String, value: String) {
        TODO("Not yet implemented")
        sharedPreferences.edit().putString(key, value).apply()
    }
}