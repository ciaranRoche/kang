package org.ciaranroche.kang.fragments.startup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import org.ciaranroche.kang.R
import org.ciaranroche.kang.activities.MainActivity
import org.ciaranroche.kang.main.MainApp
import org.ciaranroche.kang.models.user.UserFireStore
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.GoogleAuthProvider
import org.ciaranroche.kang.models.user.UserModel

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

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this.context!!, gso)

        app = this.context!!.applicationContext as MainApp
        userFireStore = app.users as UserFireStore
        loginBtn = view.findViewById(R.id.loginBtn)
        signupBtn = view.findViewById(R.id.signUpBtn)
        progressBar = view.findViewById(R.id.loginProgressBar)
        val userName = view.findViewById<TextInputEditText>(R.id.login_username)
        val userPassword = view.findViewById<TextInputEditText>(R.id.login_password)

        val googleSignInButton = view.findViewById<Button>(R.id.googleSignInBtn)

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

        googleSignInButton.setOnClickListener {
            showProgress()
            val signInIntent = googleSignInClient.getSignInIntent()
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                hideProgress()
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this.activity!!) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    if (userFireStore != null) {
                        userFireStore!!.fetchUsers {
                            if (userFireStore!!.findAll().isEmpty()) {
                                val acct = GoogleSignIn.getLastSignedInAccount(activity!!)
                                val googleUser = UserModel()
                                if (acct != null) {
                                    googleUser.username = acct.displayName!!
                                    googleUser.email = acct.email!!
                                    googleUser.userImage = acct.photoUrl.toString()
                                }
                                userFireStore!!.create(googleUser)
                                hideProgress()
                                startActivityForResult(intentFor<MainActivity>(), 0)
                            } else {
                                hideProgress()
                                startActivityForResult(intentFor<MainActivity>(), 0)
                            }
                        }
                    }
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    hideProgress()
                    toast("Authentication Failed")
                }
            }
    }
}
