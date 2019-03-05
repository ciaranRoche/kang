package org.ciaranroche.kang.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import org.ciaranroche.kang.R
import org.ciaranroche.kang.activities.MainActivity
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast

class LogInFragment : Fragment() {
    lateinit var loginBtn: Button
    lateinit var signupBtn: Button
    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log_in, container, false)
        loginBtn = view.findViewById(R.id.loginBtn)
        signupBtn = view.findViewById(R.id.signUpBtn)
        var userName = view.findViewById<TextInputEditText>(R.id.login_username)
        var userPassword = view.findViewById<TextInputEditText>(R.id.login_password)

        loginBtn.setOnClickListener { doLogin(userName.text.toString(), userPassword.text.toString()) }
        signupBtn.setOnClickListener { view -> view.findNavController().navigate(R.id.action_logInFragment_to_signUpFragment) }
        return view
    }

    fun doLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                startActivityForResult(intentFor<MainActivity>(), 0)
            } else {
                toast("Sign Up Failed: ${task.exception?.message}")
            }
        }
    }
}
