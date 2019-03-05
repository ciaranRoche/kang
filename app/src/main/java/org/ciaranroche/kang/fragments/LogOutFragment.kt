package org.ciaranroche.kang.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import org.ciaranroche.kang.R
import org.ciaranroche.kang.activities.StartUpActivity
import org.jetbrains.anko.support.v4.intentFor

class LogOutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log_out, container, false)
        val logoutBtn = view.findViewById<Button>(R.id.logoutBtn)
        val stayBtn = view.findViewById<Button>(R.id.stayBtn)

        stayBtn.setOnClickListener { view -> view.findNavController().navigate(R.id.action_logOutFragment_to_welcomeFragment) }

        logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivityForResult(intentFor<StartUpActivity>(), 0)
        }
        return view
    }
}
