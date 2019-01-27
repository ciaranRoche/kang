package org.ciaranroche.kang.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.ciaranroche.kang.helpers.exists
import org.ciaranroche.kang.helpers.read
import org.ciaranroche.kang.helpers.write
import java.util.*

val JSON_FILE = "vinyl.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<VinylModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class VinylJSONStore(val context: Context) : VinylStore {
    var vinyls = mutableListOf<VinylModel>()

    init {
        if(exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun create(vinyl: VinylModel) {
        vinyl.id = generateRandomId()
        vinyls.add(vinyl)
        serialize()
    }

    override fun update(vinyl: VinylModel) {
        var foundVinyl: VinylModel? = vinyls.find { v -> v.id == vinyl.id }
        if (foundVinyl != null){
            foundVinyl.name = vinyl.name
            foundVinyl.desc = vinyl.desc
            serialize()
        }

    }

    override fun delete(vinyl: VinylModel) {
        vinyls.remove(vinyl)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(vinyls, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        vinyls = Gson().fromJson(jsonString, listType)
    }

}