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
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import org.ciaranroche.kang.R
import org.ciaranroche.kang.activities.StartUpActivity
import org.ciaranroche.kang.main.MainApp
import org.ciaranroche.kang.models.user.UserModel
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast
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
        val collectionBtn = view.findViewById<Button>(R.id.collection_button)
        val deleteBtn = view.findViewById<Button>(R.id.deleteUserButton)

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

        deleteBtn.setOnClickListener {
            confirmDelete(user)
        }

        return view
    }

    fun confirmDelete(user: UserModel) {
        val alert = AlertDialog.Builder(context!!)

        with(alert) {
            setTitle("Confirm Account Deletion")

            setMessage("Are you sure you want to delete your account?")

            setPositiveButton("Delete") {
                    dialog, _ ->
                dialog.dismiss()
                deleteUser(user)
            }

            setNegativeButton("Cancel") {
                    dialog, _ ->
                dialog.dismiss()
            }
        }

        val dialog = alert.create()
        dialog.show()
    }

    fun deleteUser(user: UserModel) {
        app.users.delete(user)
        startActivityForResult(intentFor<StartUpActivity>(), 0)
        toast("${user.username} Deleted")
    }
}
