package org.ciaranroche.kang.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import org.ciaranroche.kang.R
import org.ciaranroche.kang.main.MainApp
import org.ciaranroche.kang.models.user.UserModel

class UserProfileFragment : Fragment() {
    lateinit var app: MainApp
    lateinit var user: UserModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)
        app = this.context!!.applicationContext as MainApp
        user = app.users.findUser(FirebaseAuth.getInstance().currentUser!!.email!!)

        val userName = view.findViewById<TextView>(R.id.userName)
        val userBio = view.findViewById<TextView>(R.id.userBio)
        val settingsFab = view.findViewById<FloatingActionButton>(R.id.settings_fab)

        userName.text = user.username
        userBio.text = user.bio

        settingsFab.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_userProfileFragment_to_settings_dest)
        }

        return view
    }
}
