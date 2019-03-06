package org.ciaranroche.kang.fragments.startup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import com.google.android.material.textfield.TextInputEditText
import org.ciaranroche.kang.R
import org.ciaranroche.kang.activities.MainActivity
import org.ciaranroche.kang.listeners.GenreSpinnerListener
import org.ciaranroche.kang.models.genre.GenreModel
import org.ciaranroche.kang.models.user.UserFireStore
import org.ciaranroche.kang.models.user.UserModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.intentFor

class SignUpUserDetailsFragment : Fragment() {
    lateinit var spinner: Spinner
    lateinit var acceptBtn: Button
    lateinit var username: TextInputEditText
    lateinit var userbio: TextInputEditText
    lateinit var favGenre: GenreModel
    lateinit var profileImage: ImageView
    lateinit var userImageBtn: Button
    lateinit var user: UserModel
    lateinit var userFireStore: UserFireStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getParcelable("user")!!
        }
        userFireStore = UserFireStore(this.context!!.applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_user_details, container, false)
        acceptBtn = view.findViewById(R.id.acceptBtn)
        username = view.findViewById(R.id.username)
        userbio = view.findViewById(R.id.userbio)
        spinner = view.findViewById(R.id.genres_spinner)

        ArrayAdapter.createFromResource(
            this.context!!,
            R.array.genres_array,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val genreListener = GenreSpinnerListener()
        spinner.onItemSelectedListener = genreListener

        acceptBtn.setOnClickListener {
            if (userFireStore != null) {
                user.username = username.text.toString()
                user.bio = userbio.text.toString()
                user.favGenre = genreListener.genre
                doAsync {
                    userFireStore!!.create(user.copy())
                    startActivityForResult(intentFor<MainActivity>(), 0)
                }
            }
        }

        return view
    }
}
