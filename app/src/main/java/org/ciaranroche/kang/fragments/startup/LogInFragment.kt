package org.ciaranroche.kang.fragments.startup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import org.ciaranroche.kang.R
import org.ciaranroche.kang.activities.MainActivity
import org.ciaranroche.kang.main.MainApp
import org.ciaranroche.kang.models.user.UserFireStore
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast

class LogInFragment : Fragment() {
    lateinit var loginBtn: Button
    lateinit var signupBtn: Button
    lateinit var progressBar: ProgressBar
    lateinit var app: MainApp

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var userFireStore: UserFireStore? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log_in, container, false)
        app = this.context!!.applicationContext as MainApp
        userFireStore = app.users as UserFireStore
        loginBtn = view.findViewById(R.id.loginBtn)
        signupBtn = view.findViewById(R.id.signUpBtn)
        progressBar = view.findViewById(R.id.loginProgressBar)
        val userName = view.findViewById<TextInputEditText>(R.id.login_username)
        val userPassword = view.findViewById<TextInputEditText>(R.id.login_password)

        hideProgress()

        loginBtn.setOnClickListener {
            if (userName.text!!.isEmpty() || userPassword.text!!.isEmpty()) {
                toast("Please fill out both fields")
            } else {
                showProgress()
                doLogin(userName.text.toString(), userPassword.text.toString())
            }
        }

        signupBtn.setOnClickListener { view -> view.findNavController().navigate(R.id.action_logInFragment_to_signUpFragment) }
        return view
    }

    fun doLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (userFireStore != null) {
                    userFireStore!!.fetchUsers {
                        hideProgress()
                        startActivityForResult(intentFor<MainActivity>(), 0)
                    }
                }
            } else {
                hideProgress()
                toast("Sign Up Failed: ${task.exception?.message}")
            }
        }
    }

    fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progressBar.visibility = View.GONE
    }
}
