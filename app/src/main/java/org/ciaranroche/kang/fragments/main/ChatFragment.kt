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
import java.util.Date

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

        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        val listOfMessages = view.findViewById<ListView>(R.id.list_of_messages)

        fab.setOnClickListener {
            val input = view.findViewById<EditText>(R.id.input)

            FirebaseDatabase.getInstance()
                .reference
                .child("messages")
                .push()
                .setValue(
                    ChatModel(
                        input.text.toString(),
                        user.username,
                        Date().time
                    )
                )

            input.setText("")
        }

        adapter = object : FirebaseListAdapter<ChatModel>(
            this.activity, ChatModel::class.java,
            R.layout.message, FirebaseDatabase.getInstance().reference.child("messages")
        ) {
            @SuppressLint("SimpleDateFormat")
            override fun populateView(view: View, model: ChatModel, position: Int) {
                val messageText = view.findViewById<TextView>(R.id.message_text)
                val messageUser = view.findViewById<TextView>(R.id.message_user)
                val messageTime = view.findViewById<TextView>(R.id.message_time)

                messageText.text = model.message
                messageUser.text = model.user

                messageTime.text = SimpleDateFormat("dd MMM YYYY").format(model.time)
            }
        }

        listOfMessages.adapter = adapter
        return view
    }
}
