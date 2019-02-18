package org.ciaranroche.kang.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import org.ciaranroche.kang.R
import org.ciaranroche.kang.helpers.addNewRating
import org.ciaranroche.kang.helpers.returnRating
import org.ciaranroche.kang.main.MainApp
import org.ciaranroche.kang.models.VinylModel



class VinylMoreFragment : Fragment() {

    lateinit var vinyl: VinylModel
    lateinit var app : MainApp
    lateinit var ratingBar: RatingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            vinyl = it.getParcelable("vinyl")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vinyl_more, container, false)

        app = this.context!!.applicationContext as MainApp

        val artistTitle = view.findViewById<TextView>(R.id.artist_name)
        val vinylTitle = view.findViewById<TextView>(R.id.album_title)
        val vinylDesc = view.findViewById<TextView>(R.id.album_desc)
        val vinylArt = view.findViewById<ImageView>(R.id.album_picture)

        ratingBar = view.findViewById<RatingBar>(R.id.album_rating)

        val deleteBtn = view.findViewById<FloatingActionButton>(R.id.delete_button)

        artistTitle.text = vinyl.artist
        vinylTitle.text = vinyl.name
        vinylDesc.text = vinyl.desc
        ratingBar.rating = returnRating(vinyl.rating).toFloat()

        if(vinyl.image != ""){
            Picasso.get()
                .load(vinyl.image)
                .resize(1080,1080)
                .centerCrop()
                .into(vinylArt)
        }

        deleteBtn.setOnClickListener { view ->
            app.vinyls.delete(vinyl)
            view.findNavController().navigate(R.id.action_vinylMoreFragment_to_thankYouFragment)
        }

        ratingBar.setOnRatingBarChangeListener { ratingBar, fl, b ->
            vinyl.rating = addNewRating(fl.toInt(), vinyl.rating)
            app.vinyls.update(vinyl)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VinylMoreFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("vinyl", vinyl)
                }
            }
    }
}
