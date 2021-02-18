package com.mandiri.news.data.local

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SessionPref {
    private var sharedPreferences: SharedPreferences? = null
    private var spEditor: SharedPreferences.Editor? = null
    private val NAME_SESSION = "brotenthon"
    private val SESSION = "session"
    private val STATUS = "status"
    private val FIRE = "fire"
    private val PUSH = "PU"
    private val TN = "TN"
    private val NOTIF = "notif"
    val KOSONG = "tenthon"

    companion object {
        @SuppressLint("CommitPrefEdits")
        fun instance(context: Context): SessionPref {
            val session = SessionPref()
            session.sharedPreferences =
                context.getSharedPreferences(session.NAME_SESSION, Context.MODE_PRIVATE)
            session.spEditor = session.sharedPreferences!!.edit()
            return session
        }
    }
}