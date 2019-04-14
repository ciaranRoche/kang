package org.ciaranroche.kang.models.user

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.ciaranroche.kang.helpers.readImageFromPath
import java.io.ByteArrayOutputStream
import java.io.File

class UserFireStore(val context: Context) : UserStore {
    val users = ArrayList<UserModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference
    lateinit var st: StorageReference

    override fun create(user: UserModel) {
        fetchUsers { }
        val key = db.child("users").child("profile").push().key
        user.fbid = key!!
        users.add(user)
        db.child("users").child(userId).child("profile").child(key).setValue(user)
        updateImage(user)
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
        if (user.userImage[0] != 'h') {
            updateImage(user)
        }
    }

    override fun delete(user: UserModel) {
        fetchUsers { }
        db.child("users").child(userId).child("profile").child(user.fbid).removeValue()
        users.remove(user)
    }

    override fun findUser(email: String): UserModel {
        val foundUser: UserModel? = users.find { u -> u.email == email }
        return foundUser!!
    }

    override fun findAll(): MutableList<UserModel> {
        return users
    }

    fun updateImage(user: UserModel) {
        fetchUsers { }
        if (user.userImage != "") {
            val fileName = File(user.userImage)
            val imageName = fileName.getName()

            var imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, user.userImage)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        user.userImage = it.toString()
                        db.child("users").child(userId).child("profile").child(user.fbid).setValue(user)
                    }
                }
            }
        }
    }

    fun fetchUsers(usersReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.iterator().forEach {
                    users.add(it.getValue<UserModel>(UserModel::class.java)!!)
                }
                usersReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        st = FirebaseStorage.getInstance().reference
        users.clear()
        db.child("users").child(userId).child("profile").addListenerForSingleValueEvent(valueEventListener)
    }
}