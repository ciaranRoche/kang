package org.ciaranroche.kang.fragments

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
import org.ciaranroche.kang.models.VinylJSONStore
import org.ciaranroche.kang.models.VinylModel

class AddVinylFragment : Fragment() {

    var vinyl = VinylModel()

    lateinit var data: VinylJSONStore
    lateinit var addBtn: Button
    lateinit var vinylName: TextInputEditText
    lateinit var artistName: TextInputEditText
    lateinit var albumDesc: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_vinyl_fragment, container, false)

        data = VinylJSONStore(this.context!!.applicationContext)

        addBtn = view.findViewById(R.id.next_button)
        vinylName = view.findViewById(R.id.vinyl_name_input)
        artistName = view.findViewById(R.id.artist_name_input)
        albumDesc = view.findViewById(R.id.vinyl_desc_input)

        addBtn.setOnClickListener {
            view ->
            vinyl.name = vinylName.text.toString()
            vinyl.artist = artistName.text.toString()
            vinyl.desc = albumDesc.text.toString()
            data.create(vinyl)
            Log.i("boop", data.findAll().toString())
            view.findNavController().navigate(R.id.action_add_vinyl_to_vinylProfileFragment2)
            Toast.makeText(this.context, "I work", Toast.LENGTH_LONG).show()
        }

        return view
    }

}
