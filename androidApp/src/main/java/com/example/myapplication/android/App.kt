package com.example.myapplication.android

import android.app.Application
import com.example.myapplication.UserManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        UserManager.initialize(this)
    }
}