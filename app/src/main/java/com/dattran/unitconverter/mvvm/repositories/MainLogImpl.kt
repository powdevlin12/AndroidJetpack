package com.dattran.unitconverter.mvvm.repositories

import javax.inject.Inject

class MainLogImpl @Inject constructor() : MainLog {
    override fun d(tag: String, msg: String) {
        android.util.Log.d(tag, msg)
    }

    override fun i(tag: String, msg: String) {
        android.util.Log.i(tag, msg)
    }

    override fun e(tag: String, msg: String) {
        android.util.Log.e(tag, msg)
    }
}