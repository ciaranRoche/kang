package org.ciaranroche.kang.listeners

import android.util.Log
import android.view.View
import android.widget.AdapterView
import org.ciaranroche.kang.helpers.seedGenres
import org.ciaranroche.kang.models.genre.GenreModel

class GenreSpinnerListener : AdapterView.OnItemSelectedListener{

    lateinit var genre: GenreModel
    lateinit var genreList: ArrayList<GenreModel>

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        genreList = seedGenres()
        genre = when (parent?.getItemAtPosition(pos).toString()) {
            "Traditional" -> genreList.find { g -> g.title == "Traditional"}!!
            "Black" -> genreList.find { g -> g.title == "Black" }!!
            "Death" -> genreList.find { g -> g.title == "Death" }!!
            "Funeral" -> genreList.find { g -> g.title == "Funeral" }!!
            "Drone" -> genreList.find { g -> g.title == "Drone" }!!
            "Gothic" -> genreList.find { g -> g.title == "Gothic" }!!
            "Progressive" -> genreList.find { g -> g.title == "Progressive" }!!
            "Sludge" -> genreList.find { g -> g.title == "Sludge" }!!
            "Stoner" -> genreList.find { g -> g.title == "Stoner" }!!
            else -> {
                genreList.find { g -> g.title == "Traditional"}!!
            }

        }
    }

}