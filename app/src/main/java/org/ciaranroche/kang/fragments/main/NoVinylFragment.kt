package org.ciaranroche.kang.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import org.ciaranroche.kang.R

class NoVinylFragment : Fragment() {
    lateinit var mveBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_no_vinyl, container, false)
        mveBtn = view.findViewById(R.id.move_button)

        mveBtn.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_noVinylFragment_to_add_vinyl)
        }
        return view
    }
}
