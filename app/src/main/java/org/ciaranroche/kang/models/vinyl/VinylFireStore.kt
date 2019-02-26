package org.ciaranroche.kang.models.vinyl

import android.content.Context
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import org.ciaranroche.kang.R
import org.ciaranroche.kang.models.genre.GenreModel
import org.ciaranroche.kang.models.rating.RatingModel

class VinylFireStore(val context: Context) : VinylStore {
    val vinyls = ArrayList<VinylModel>()
    var appId = "kang-col"
    lateinit var db: DatabaseReference

    override fun seed() {
        create(VinylModel(0, "", "Monolord", "Empress Rising", "Empress Rising is the band’s first full-length release and consists of “only” 5 songs, though the album clocks in an impressive length of over 45 minutes. First track is the title track, which is also the longest track on the album, being just over 12 minutes in length.", "https://i.gyazo.com/d80318222c3c227094d02797e1bd24c5.jpg", GenreModel("Stoner", "Describes doom metal that incorporates psychedelic rock and acid rock elements  is often heavily distorted, groove-laden bass-heavy sound, making much use of guitar effects such as fuzz, phaser, or flanger.", R.color.colorAccent),
            RatingModel(4, 5, 6, 7, 3, 2)
        ))
        create(VinylModel(0, "", "Ahab", "The Giant", "Ahab is at the tip of the spear of the funeral doom movement, and The Giant is their third foray into their unique, nautical-themed whale tales. While their core sound remains one of glacial doom/death", "https://i.gyazo.com/a9b1dc25982093f5e6aaee69c3bbce37.jpg", GenreModel("Funeral", "Funeral doom is a genre that crosses death-doom with funeral dirge music. It is played at a very slow tempo, and places an emphasis on evoking a sense of emptiness and despair.", R.color.colorPrimaryDark), RatingModel(19, 0, 5, 2, 1, 0)))
    }

    override fun create(vinyl: VinylModel) {
        fetchVinyls {}
        val key = db.child("collections").child(appId).child("vinyls").push().key
        vinyl.fbid = key!!
        db.child("collections").child(appId).child("vinyls").child(key).setValue(vinyl)
    }

    override fun update(vinyl: VinylModel) {
        fetchVinyls {}
        var foundVinyl: VinylModel? = vinyls.find { v -> v.fbid == vinyl.fbid }
        if (foundVinyl != null) {
            foundVinyl.artist = vinyl.artist
            foundVinyl.desc = vinyl.desc
            foundVinyl.name = vinyl.name
            foundVinyl.image = vinyl.image
        }
        db.child("collections").child(appId).child("vinyls").child(vinyl.fbid).setValue(vinyl)
    }

    override fun delete(vinyl: VinylModel) {
        fetchVinyls {}
        db.child("collections").child(appId).child("vinyls").child(vinyl.fbid).removeValue()
        vinyls.remove(vinyl)
    }

    override fun findAll(): MutableList<VinylModel> {
        fetchVinyls {}
        return vinyls
    }

    fun fetchVinyls(vinylsReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // dataSnapshot.children.mapNotNullTo(vinyls) { it.getValue<VinylModel>(VinylModel::class.java)}
                dataSnapshot.children.iterator().forEach { vinyls.add(it.getValue<VinylModel>(
                    VinylModel::class.java)!!) }
                vinylsReady()
            }
        }
        db = FirebaseDatabase.getInstance().reference
        vinyls.clear()
        db.child("collections").child(appId).child("vinyls").addListenerForSingleValueEvent(valueEventListener)
    }
}