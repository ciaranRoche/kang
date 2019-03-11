package org.ciaranroche.kang.fragments.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.ciaranroche.kang.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import android.widget.EditText
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.ciaranroche.kang.main.MainApp
import org.ciaranroche.kang.models.chat.ChatModel
import org.ciaranroche.kang.models.user.UserModel
import com.firebase.ui.database.FirebaseListAdapter
import android.widget.TextView
import java.text.SimpleDateFormat

class ChatFragment : Fragment() {
    lateinit var app: MainApp
    lateinit var user: UserModel
    private var adapter: FirebaseListAdapter<ChatModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        app = this.context!!.applicationContext as MainApp
        user = app.users.findUser(FirebaseAuth.getInstance().currentUser!!.email!!)

        val fab = view.findViewById(R.id.fab) as FloatingActionButton

        fab.setOnClickListener {
            val input = view.findViewById(R.id.input) as EditText

            FirebaseDatabase.getInstance()
                .reference
                .push()
                .setValue(
                    ChatModel(
                        input.text.toString(),
                        user.username
                    )
                )

            input.setText("")
        }

        val listOfMessages = view.findViewById(R.id.list_of_messages) as ListView

        adapter = object : FirebaseListAdapter<ChatModel>(
            this.activity, ChatModel::class.java,
            R.layout.message, FirebaseDatabase.getInstance().reference
        ) {
            @SuppressLint("SimpleDateFormat")
            override fun populateView(v: View, model: ChatModel, position: Int) {
                val messageText = v.findViewById(R.id.message_text) as TextView
                val messageUser = v.findViewById(R.id.message_user) as TextView
                val messageTime = v.findViewById(R.id.message_time) as TextView

                messageText.text = model.message
                messageUser.text = model.user

                messageTime.text = SimpleDateFormat("dd MMM YYYY").format(model.time)
            }
        }

        listOfMessages.adapter = adapter
        return view
    }
}
