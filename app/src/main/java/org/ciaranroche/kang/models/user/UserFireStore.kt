package org.ciaranroche.kang.models.user

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class UserFireStore(val context: Context) : UserStore {
    val users = ArrayList<UserModel>()
    lateinit var user: UserModel
    lateinit var userId: String
    lateinit var db: DatabaseReference
    var appId = "kang-col"

    override fun create(user: UserModel) {
        fetchUsers { }
        val key = db.child("users").child("profile").push().key
        user.fbid = key!!
        users.add(user)
        db.child("users").child(userId).child("profile").child(key).setValue(user)
    }

    override fun update(user: UserModel) {
        fetchUsers { }
        var foundUser: UserModel? = users.find { u -> u.fbid == user.fbid }
        if (foundUser != null) {
            foundUser.username = user.username
            foundUser.email = user.email
            foundUser.age = user.age
            foundUser.bio = user.bio
            foundUser.userImage = user.userImage
            foundUser.favGenre = user.favGenre
            foundUser.favVinyl = user.favVinyl
        }
        db.child("users").child(userId).child("profile").child(user.fbid).setValue(user)
    }

    override fun delete(user: UserModel) {
        fetchUsers { }
        db.child("users").child(userId).child("profile").child(user.fbid).removeValue()
        users.remove(user)
    }

    override fun findUser(email: String): UserModel {
        fetchUsers { }
        val foundUser: UserModel? = users.find { u -> u.email == email }
        return foundUser!!
    }

    fun fetchUsers(usersReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.iterator().forEach { it.getValue<UserModel>(UserModel::class.java) }
                usersReady
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        users.clear()
        db.child("users").child(userId).child("profile").addListenerForSingleValueEvent(valueEventListener)
    }
}