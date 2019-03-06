package org.ciaranroche.kang.fragments.startup

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
import org.ciaranroche.kang.models.user.UserModel
import org.jetbrains.anko.support.v4.toast

class SignUpFragment : Fragment() {
    lateinit var loginBtn: Button
    lateinit var signupBtn: Button
    var user = UserModel()
    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        loginBtn = view.findViewById(R.id.loginBtn)
        signupBtn = view.findViewById(R.id.signUpBtn)
        val userEmail = view.findViewById<TextInputEditText>(R.id.signupEmail)
        val userPassword = view.findViewById<TextInputEditText>(R.id.signupPassword)

        signupBtn.setOnClickListener { doSignUp(userEmail.text.toString(), userPassword.text.toString(), view) }
        loginBtn.setOnClickListener { view -> view.findNavController().navigate(R.id.action_signUpFragment_to_logInFragment) }
        return view
    }

    fun doSignUp(email: String, password: String, view: View) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                user.email = email
                val bundle = Bundle()
                bundle.putParcelable("user", user)
                view.findNavController().navigate(R.id.action_signUpFragment_to_signUpUserDetailsFragment, bundle)
            } else {
                toast("Sign Up Failed: ${task.exception?.message}")
            }
        }
    }
}
