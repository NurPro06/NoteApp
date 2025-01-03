package com.example.noteapp.utlis

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {
    private lateinit var sharedPref: SharedPreferences

    fun init(context: Context) {
        sharedPref = context.getSharedPreferences("shared", Context.MODE_PRIVATE)

    }
    var text : Boolean
        get() = sharedPref.getBoolean("text", false)
        set(value)= sharedPref.edit().putBoolean("text", value).apply()



    fun setOnBoardingCompleted(b: Boolean) {
        sharedPref.edit().putBoolean("onBoardingCompleted", b).apply()
    }

    fun isOnBoardingCompleted(b: Boolean): Boolean {
        return sharedPref.getBoolean("onBoardingCompleted", true)
    }
    var layoutManager: Boolean
        get() = sharedPref.getBoolean("layoutManager", true)
        set(value) = sharedPref.edit().putBoolean("layoutManager", value).apply()

    fun isLinearLayout(): Boolean {
        return sharedPref.getBoolean("isLinearLayout", true)
    }
    fun setLinearLayout(isLinearLayout: Boolean) {
        sharedPref.edit().putBoolean("isLinearLayout", isLinearLayout).apply()
    }
    fun setRegistered(isRegistered: Boolean) {
        sharedPref.edit().putBoolean("isRegistered", isRegistered).apply()
    }


    fun isRegistered(): Boolean {
        return sharedPref.getBoolean("isRegistered", false)
    }
}