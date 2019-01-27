package org.ciaranroche.kang.main

import android.app.Application
import android.util.Log
import org.ciaranroche.kang.models.VinylJSONStore
import org.ciaranroche.kang.models.VinylStore

class MainApp : Application() {
    lateinit var  vinyls: VinylStore

    override fun onCreate() {
        super.onCreate()
        vinyls = VinylJSONStore(applicationContext)
        Log.i("start", "Application Started")
    }
}