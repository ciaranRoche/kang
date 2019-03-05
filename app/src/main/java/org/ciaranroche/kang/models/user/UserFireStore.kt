package org.ciaranroche.kang.models.user

import android.content.Context
import com.google.firebase.database.*

class UserFireStore(val context: Context) : UserStore {
    val users = ArrayList<UserModel>()
    lateinit var user : UserModel
    lateinit var db: DatabaseReference
    var appId = "kang-col"

    override fun create(user: UserModel) {
    }

    override fun update(user: UserModel) {
    }

    override fun delete(user: UserModel) {
    }

    override fun find(user: UserModel) {
    }

    fun fetchUsers(usersReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.iterator().forEach {  it.getValue<UserModel>(UserModel::class.java) }
                usersReady
            }
        }
        db = FirebaseDatabase.getInstance().reference
        users.clear()
        db.child("users").child(appId).child("profile").addListenerForSingleValueEvent(valueEventListener)
    }
}