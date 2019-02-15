package org.ciaranroche.kang.models

import android.content.Context
import android.util.Log
import com.google.firebase.database.*



class VinylFireStore(val context: Context) : VinylStore {
    val vinyls = ArrayList<VinylModel>()
    var appId = "kang-col"
    lateinit var db: DatabaseReference

    override fun create(vinyl: VinylModel) {
        fetchVinyls {}
        val key = db.child("collections").child(appId).child("vinyls").push().key
        vinyl.fbid = key!!
        db.child("collections").child(appId).child("vinyls").child(key).setValue(vinyl)
    }

    override fun update(vinyl: VinylModel) {
        fetchVinyls {}
        var foundVinyl: VinylModel? = vinyls.find { v -> v.fbid == vinyl.fbid }
        if (foundVinyl != null){
            foundVinyl.artist = vinyl.artist
            foundVinyl.desc = vinyl.desc
            foundVinyl.name = vinyl.name
            foundVinyl.image = vinyl.image
        }
    }

    override fun delete(vinyl: VinylModel) {
        fetchVinyls {}
        db.child("collections").child(appId).child("vinyls").child(vinyl.fbid).removeValue()
        vinyls.remove(vinyl)
    }

    override fun findAll(): MutableList<VinylModel> {
        fetchVinyls {}
        Log.i("boop-find", vinyls.toString())
        return vinyls
    }

    fun fetchVinyls(vinylsReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(error : DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.i("boop-boo", dataSnapshot.toString())
                //dataSnapshot.children.mapNotNullTo(vinyls) { it.getValue<VinylModel>(VinylModel::class.java)}
                dataSnapshot.children.iterator().forEach { vinyls.add(it.getValue<VinylModel>(VinylModel::class.java)!!) }
                Log.i("boop-booo", vinyls.toString())
                vinylsReady()
            }
        }
        Log.i("boop-boop", vinyls.toString())
        db = FirebaseDatabase.getInstance().reference
        vinyls.clear()
        db.child("collections").child(appId).child("vinyls").addListenerForSingleValueEvent(valueEventListener)
    }
}