package org.ciaranroche.kang.fragments.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import org.ciaranroche.kang.R
import org.ciaranroche.kang.helpers.showImagePicker
import org.ciaranroche.kang.listeners.GenreSpinnerListener
import org.ciaranroche.kang.main.MainApp
import org.ciaranroche.kang.models.user.UserModel
import org.jetbrains.anko.support.v4.toast

class SettingsFragment : Fragment() {
    var IMAGE_REQUEST = 1
    var imageLocation = ""
    var newImage = false

    lateinit var app: MainApp
    lateinit var user: UserModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        app = this.context!!.applicationContext as MainApp
        user = app.users.findUser(FirebaseAuth.getInstance().currentUser!!.email!!)

        val userName = view.findViewById<TextView>(R.id.set_username)
        val userBio = view.findViewById<TextView>(R.id.set_bio)
        val imageBtn = view.findViewById<Button>(R.id.image_button)
        val acceptBtn = view.findViewById<Button>(R.id.accept_button)
        val spinner = view.findViewById<Spinner>(R.id.genres_spinner)

        userName.text = user.username
        userBio.text = user.bio

        ArrayAdapter.createFromResource(
            this.context!!,
            R.array.genres_array,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val genreListener = GenreSpinnerListener()
        spinner.onItemSelectedListener = genreListener

        acceptBtn.setOnClickListener {
            user.username = userName.text.toString()
            user.bio = userBio.text.toString()
            user.favGenre = genreListener.genre
            if (newImage) {
                user.userImage = imageLocation
            }
            app.users.update(user)
            toast("Profile Updated")
        }

        imageBtn.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    val image: String = data.data.toString()
                    newImage = true
                    imageLocation = image
                }
            }
        }
    }
}
