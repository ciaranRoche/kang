package org.ciaranroche.kang.main

import android.app.Application
import android.util.Log
import org.ciaranroche.kang.models.VinylFireStore
import org.ciaranroche.kang.models.VinylJSONStore
import org.ciaranroche.kang.models.VinylModel
import org.ciaranroche.kang.models.VinylStore

class MainApp : Application() {
    lateinit var vinyls: VinylStore
    lateinit var vinylsList: ArrayList<VinylModel>
    lateinit var genreList: ArrayList<String>

    override fun onCreate() {
        super.onCreate()
        vinyls = VinylFireStore(applicationContext)
        vinylsList = vinyls.findAll() as ArrayList<VinylModel>
        genreList = ArrayList()
        genreList.add("Doom")
        genreList.add("Sludge")
        Log.i("start", "Application Started")
    }
}