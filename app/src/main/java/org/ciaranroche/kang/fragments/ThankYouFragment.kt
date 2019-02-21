package org.ciaranroche.kang.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import org.ciaranroche.kang.R

class ThankYouFragment : Fragment() {
    lateinit var mveBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_thank_you, container, false)
        mveBtn = view.findViewById(R.id.move_button)

        mveBtn.setOnClickListener { view -> view.findNavController().navigate(R.id.action_thankYouFragment_to_welcomeFragment)
        }
        return view
    }
}
