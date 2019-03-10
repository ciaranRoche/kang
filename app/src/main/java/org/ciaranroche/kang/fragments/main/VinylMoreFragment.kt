package org.ciaranroche.kang.fragments.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import org.ciaranroche.kang.R
import org.ciaranroche.kang.helpers.addNewRating
import org.ciaranroche.kang.helpers.returnRating
import org.ciaranroche.kang.main.MainApp
import org.ciaranroche.kang.models.user.UserModel
import org.ciaranroche.kang.models.vinyl.VinylModel
import org.jetbrains.anko.support.v4.toast

class VinylMoreFragment : Fragment() {

    lateinit var vinyl: VinylModel
    lateinit var user: UserModel
    lateinit var app: MainApp
    lateinit var ratingBar: RatingBar
    lateinit var favBtn: Button

    private val inColBtn = "Remove from Collection"
    private val notInColBtn = "Add to Collection"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            vinyl = it.getParcelable("vinyl")!!
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vinyl_more, container, false)

        app = this.context!!.applicationContext as MainApp
        user = app.users.findUser(FirebaseAuth.getInstance().currentUser!!.email!!)

        val artistTitle = view.findViewById<TextView>(R.id.artist_name)
        val vinylTitle = view.findViewById<TextView>(R.id.album_title)
        val vinylDesc = view.findViewById<TextView>(R.id.album_desc)
        val vinylArt = view.findViewById<ImageView>(R.id.album_picture)
        favBtn = view.findViewById(R.id.favBtn)

        if (inCollection()) {
            favBtn.text = inColBtn
        }

        ratingBar = view.findViewById<RatingBar>(R.id.album_rating)

        val deleteBtn = view.findViewById<Button>(R.id.deleteBtn)

        artistTitle.text = vinyl.artist
        vinylTitle.text = vinyl.name
        vinylDesc.text = vinyl.desc
        ratingBar.rating = returnRating(vinyl.rating).toFloat()

        if (vinyl.image != "") {
            Picasso.get()
                .load(vinyl.image)
                .resize(1080, 1080)
                .centerCrop()
                .into(vinylArt)
        }

        deleteBtn.setOnClickListener { view ->
            confirmDelete(vinyl, view)
        }

        ratingBar.setOnRatingBarChangeListener { ratingBar, fl, b ->
            vinyl.rating = addNewRating(fl.toInt(), vinyl.rating)
            app.vinyls.update(vinyl)
        }

        favBtn.setOnClickListener {
            if (inCollection()) {
                user.favVinyl.remove(vinyl)
                app.users.update(user)
                favBtn.text = notInColBtn
                toast("${vinyl.name} removed from your collection")
            } else {
                user.favVinyl.add(vinyl)
                app.users.update(user)
                favBtn.text = inColBtn
                toast("${vinyl.name} added to your collection")
            }
        }

        return view
    }

    fun inCollection(): Boolean {
        val foundVinyl = user.favVinyl.find { v -> v.name == vinyl.name }
        if (foundVinyl != null) {
            return true
        }
        return false
    }

    fun confirmDelete(vinyl: VinylModel, view: View) {
        val alert = AlertDialog.Builder(context!!)

        with(alert) {
            setTitle("Confirm Vinyl Deletion")

            setMessage("Are you sure you want to delete this record from Kang?")

            setPositiveButton("Delete") {
                    dialog, _ ->
                dialog.dismiss()
                deleteUser(vinyl, view)
            }

            setNegativeButton("Cancel") {
                    dialog, _ ->
                dialog.dismiss()
            }
        }

        val dialog = alert.create()
        dialog.show()
    }

    fun deleteUser(vinyl: VinylModel, view: View) {
        app.vinyls.delete(vinyl)
        view.findNavController().navigate(R.id.action_vinylMoreFragment_to_thankYouFragment)
        toast("${user.username} Deleted")
    }
}
