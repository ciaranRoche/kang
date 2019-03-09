package org.ciaranroche.kang.fragments.main

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import org.ciaranroche.kang.R
import org.ciaranroche.kang.main.MainApp
import org.ciaranroche.kang.models.user.UserModel
import java.util.ArrayList

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
        val userImage = view.findViewById<ImageView>(R.id.imageView)
        val settingsFab = view.findViewById<FloatingActionButton>(R.id.settings_fab)
        var collectionBtn = view.findViewById<Button>(R.id.collection_button)

        userName.text = user.username
        userBio.text = user.bio
        if (user.userImage.isNotEmpty()) {
            Picasso.get()
                .load(user.userImage)
                .resize(1080, 1080)
                .centerCrop()
                .into(userImage)
        }

        settingsFab.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_userProfileFragment_to_settings_dest)
        }

        collectionBtn.setOnClickListener { view ->
            val bundle = Bundle()
            bundle.putParcelableArrayList("vinyl", user.favVinyl as ArrayList<out Parcelable>)
            view.findNavController().navigate(R.id.action_userProfileFragment_to_vinylProfileFragment, bundle)
        }

        return view
    }
}
