package org.ciaranroche.kang.main

import android.app.Application
import org.ciaranroche.kang.helpers.seedGenres
import org.ciaranroche.kang.models.genre.GenreModel
import org.ciaranroche.kang.models.user.UserFireStore
import org.ciaranroche.kang.models.user.UserModel
import org.ciaranroche.kang.models.user.UserStore
import org.ciaranroche.kang.models.vinyl.VinylFireStore
import org.ciaranroche.kang.models.vinyl.VinylModel
import org.ciaranroche.kang.models.vinyl.VinylStore

class MainApp : Application() {
    lateinit var vinyls: VinylStore
    lateinit var users: UserStore
    lateinit var vinylsList: ArrayList<VinylModel>
    lateinit var userList: ArrayList<UserModel>
    lateinit var genreList: ArrayList<GenreModel>

    override fun onCreate() {
        super.onCreate()
        vinyls = VinylFireStore(applicationContext)
        users = UserFireStore(applicationContext)
        // vinyls.seed()
        vinylsList = vinyls.findAll() as ArrayList<VinylModel>
        userList = users.findAll() as ArrayList<UserModel>
        genreList = seedGenres()
    }
}