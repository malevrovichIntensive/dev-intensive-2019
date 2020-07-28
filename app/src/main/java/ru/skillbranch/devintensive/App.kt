package ru.skillbranch.devintensive

import android.app.Application
import android.content.Context
import android.util.Log

class App: Application() {
    companion object{
        private lateinit var instance: App

        fun applicationContext(): Context{
            return instance.applicationContext
        }
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("Created", "Created")
    }
}