package org.ciaranroche.kang.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import org.ciaranroche.kang.R


class AddVinylFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val safeArgs = AddVinylFragmentArgs.fromBundle(arguments!!)
        val flowStepNumber = safeArgs.flowStepNumber

        return when (flowStepNumber) {
            2 -> inflater.inflate(R.layout.add_vinyl_two_fragment, container, false)
            else -> inflater.inflate(R.layout.add_vinyl_fragment, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.next_button).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.next_action)
        )
    }
}
