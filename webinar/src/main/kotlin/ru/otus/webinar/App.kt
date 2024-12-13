package ru.otus.webinar

import android.app.Application
import ru.otus.webinar.data.SessionManager

class App : Application() {

    lateinit var sessionManager: SessionManager

    override fun onCreate() {
        super.onCreate()
        sessionManager = SessionManager.Impl(this)
    }
}