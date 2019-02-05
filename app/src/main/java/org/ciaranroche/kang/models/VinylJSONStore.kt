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
            foundVinyl.artist = vinyl.artist
            foundVinyl.name = vinyl.name
            foundVinyl.desc = vinyl.desc
            foundVinyl.image = vinyl.image
            serialize()
        }

    }

    override fun delete(vinyl: VinylModel) {
        vinyls.remove(vinyl)
        serialize()
    }

    override fun findAll(): MutableList<VinylModel> {
        return vinyls
    }

    override fun seed() {
        vinyls.add(VinylModel(generateRandomId(), "Whoopie Cat", "Whoopie Cat", "Heavy blues from Australia. Bluesy and soulful with bursts of heavier rock music and deep driving riffs. ", "https://i.gyazo.com/cbe96e95bdc91fd584cfb4e0e980837f.png"))
        vinyls.add(VinylModel(generateRandomId(), "Samsara Blues Experiment", "Waiting for the flood", "SBE transcend the tag of stoner, psychedelic or progressive rock.", "https://i.gyazo.com/91271e14388c35b661e5b1d8a89a5c06.png"))
        vinyls.add(VinylModel(generateRandomId(), "All them Witches", "Dying Surfer Meets His Maker", "Nashville four-piece. Stylish and sincere blend of heavy blues, wistful psych jamming and tasteful, adventurous songwriting", "https://i.gyazo.com/450ceb82eb8f9e2afdf8c8dbe75d2678.png"))
        vinyls.add(VinylModel(generateRandomId(), "Stoned Jesus", "Seven Thunders Roar", "Doubtlessly the most interesting band to emerge on Eastern Europe`s stoner psychedelic doom scene.", "https://i.gyazo.com/ce3c3c0a3b3c266c2099438745af04d4.png"))
        vinyls.add(VinylModel(generateRandomId(), "Uncle Acid & The Deadbeats", "Wasteland", "Eagerly anticipated fifth studio album from this British heavy rock institution UNCLE ACID & THE DEADBEATS!!", "https://i.gyazo.com/b422ed74fbd630806aa1fb8665e33e57.png"))
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