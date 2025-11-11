package com.dattran.unitconverter.mvvm.repositories

import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StoreImpl @Inject constructor(
    @ApplicationContext context: android.content.Context,
    private val sharedPreferences: SharedPreferences
) : Store {

    private val storage = mutableMapOf<String, String>()

    override fun getValue(key: String): String? {
        return storage[key]
    }

    override fun setValue(key: String, value: String) {
        storage[key] = value
    }
}