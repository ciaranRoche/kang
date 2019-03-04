package org.ciaranroche.kang.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import org.ciaranroche.kang.R
import org.ciaranroche.kang.activities.MainActivity
import org.jetbrains.anko.support.v4.intentFor

class LogInFragment : Fragment() {
    lateinit var loginBtn: Button
    lateinit var signupBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_log_in, container, false)
        loginBtn = view.findViewById(R.id.loginBtn)
        signupBtn = view.findViewById(R.id.signUpBtn)

        loginBtn.setOnClickListener { startActivityForResult(intentFor<MainActivity>(), 0) }
        signupBtn.setOnClickListener { view -> view.findNavController().navigate(R.id.action_logInFragment_to_signUpFragment) }
        return view
    }
}
