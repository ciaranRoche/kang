package org.ciaranroche.kang.fragments.startup

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Button
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import com.google.android.material.textfield.TextInputEditText
import org.ciaranroche.kang.R
import org.ciaranroche.kang.activities.MainActivity
import org.ciaranroche.kang.listeners.GenreSpinnerListener
import org.ciaranroche.kang.main.MainApp
import org.ciaranroche.kang.models.user.UserFireStore
import org.ciaranroche.kang.models.user.UserModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast
import java.util.Calendar
import java.util.Date
import java.text.SimpleDateFormat

class SignUpUserDetailsFragment : Fragment() {
    lateinit var spinner: Spinner
    lateinit var acceptBtn: Button
    lateinit var username: TextInputEditText
    lateinit var userbio: TextInputEditText
    lateinit var dobBtn: Button
    lateinit var progressBar: ProgressBar

    lateinit var user: UserModel
    lateinit var app: MainApp

    var userFireStore: UserFireStore? = null
    var dob = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getParcelable("user")!!
        }
        app = this.context!!.applicationContext as MainApp
        userFireStore = app.users as UserFireStore
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
        dobBtn = view.findViewById(R.id.dobBtn)
        progressBar = view.findViewById(R.id.signupProgressBar)

        hideProgress()

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
            if (username.text!!.isEmpty() || userbio.text!!.isEmpty()) {
                toast("Please fill out all fields")
            } else if (!dob) {
                toast("Please give Data of Birth")
            } else {
                if (userFireStore != null) {
                    showProgress()
                    user.username = username.text.toString()
                    user.bio = userbio.text.toString()
                    user.favGenre = genreListener.genre
                    doAsync {
                        userFireStore!!.create(user.copy())
                        userFireStore!!.fetchUsers {
                            hideProgress()
                            startActivityForResult(intentFor<MainActivity>(), 0)
                        }
                    }
                }
            }
        }

        dobBtn.setOnClickListener {
            dob = true
            val c: Calendar = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dateDialog: DatePickerDialog = DatePickerDialog(view.context, datePickerListener, year, month, day)
            dateDialog.datePicker.maxDate = Date().time
            dateDialog.show()
        }

        return view
    }

    private val datePickerListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
        val c = Calendar.getInstance()
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, day)
        val format = SimpleDateFormat("dd MMM YYYY").format(c.time)
        user.dob = format
        user.age = calculateAge(c.timeInMillis)
    }

    fun calculateAge(date: Long): Int {
        val dob = Calendar.getInstance()
        dob.timeInMillis = date
        val today = Calendar.getInstance()
        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
            age--
        }
        return age
    }

    fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progressBar.visibility = View.GONE
    }
}