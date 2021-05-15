package com.lukitor.projectandroidfundamentalkotlin2

import android.content.Context

internal class AlarmPreferences(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val STATUS = "Status_Alarm"
    }
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setStatus(boolean: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(STATUS,boolean)
        editor.apply()
    }
    fun getStatus(): Boolean {
        return preferences.getBoolean(STATUS,false)
    }
}