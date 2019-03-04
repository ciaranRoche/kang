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

class SignUpFragment : Fragment() {
    lateinit var loginBtn: Button
    lateinit var signupBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        loginBtn = view.findViewById(R.id.loginBtn)
        signupBtn = view.findViewById(R.id.signUpBtn)

        signupBtn.setOnClickListener { startActivityForResult(intentFor<MainActivity>(), 0) }
        loginBtn.setOnClickListener { view -> view.findNavController().navigate(R.id.action_signUpFragment_to_logInFragment) }
        return view
    }
}
