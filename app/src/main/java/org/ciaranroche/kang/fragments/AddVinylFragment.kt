package org.ciaranroche.kang.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import org.ciaranroche.kang.R
import org.ciaranroche.kang.helpers.showImagePicker
import org.ciaranroche.kang.main.MainApp
import org.ciaranroche.kang.models.VinylFireStore
import org.ciaranroche.kang.models.VinylJSONStore
import org.ciaranroche.kang.models.VinylModel

class AddVinylFragment : Fragment() {

    var vinyl = VinylModel()

    lateinit var app : MainApp

    var IMAGE_REQUEST = 1
    var imageLocation = ""

    lateinit var data: VinylFireStore
    lateinit var addBtn: Button
    lateinit var imageBtn: Button
    lateinit var vinylName: TextInputEditText
    lateinit var artistName: TextInputEditText
    lateinit var albumDesc: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_vinyl_fragment, container, false)

        app = this.context!!.applicationContext as MainApp

        addBtn = view.findViewById(R.id.next_button)
        imageBtn = view.findViewById(R.id.image_button)

        vinylName = view.findViewById(R.id.vinyl_name_input)
        artistName = view.findViewById(R.id.artist_name_input)
        albumDesc = view.findViewById(R.id.vinyl_desc_input)

        addBtn.setOnClickListener {
            view ->
            vinyl.name = vinylName.text.toString()
            vinyl.artist = artistName.text.toString()
            vinyl.desc = albumDesc.text.toString()
            vinyl.image = imageLocation
            app.vinyls.create(vinyl)
            view.findNavController().navigate(R.id.action_add_vinyl_to_thankYouFragment)
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
                    imageLocation = image
                }
            }
        }
    }

}
