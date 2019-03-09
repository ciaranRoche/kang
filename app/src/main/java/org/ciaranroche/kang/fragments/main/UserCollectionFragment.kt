package org.ciaranroche.kang.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.ciaranroche.kang.R
import org.ciaranroche.kang.models.vinyl.VinylModel

class UserCollectionFragment : Fragment() {

    lateinit var vinyls: ArrayList<VinylModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_collection, container, false)
        return view
    }
}
