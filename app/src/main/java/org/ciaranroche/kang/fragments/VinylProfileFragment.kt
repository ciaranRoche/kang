package org.ciaranroche.kang.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

import org.ciaranroche.kang.R
import org.ciaranroche.kang.models.VinylModel


class VinylProfileFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    lateinit var vinyl: VinylModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            vinyl = it.getParcelable("vinyl") as VinylModel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vinyl_profile, container, false)
        val artistTitle = view.findViewById<TextView>(R.id.artist_title)
        val albumTitle = view.findViewById<TextView>(R.id.album_title)
        val albumArt = view.findViewById<ImageView>(R.id.album_art)

        vinyl = arguments!!.getParcelable("vinyl") as VinylModel

        artistTitle.text = vinyl.artist
        albumTitle.text = vinyl.name
        Picasso.get()
            .load(vinyl.image)
            .resize(50, 50)
            .centerCrop()
            .into(albumArt)
        return view
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(vinyl: VinylModel) : VinylProfileFragment {
            val args = Bundle()
            args.putParcelable("vinyl", vinyl)
            val fragment = VinylProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
