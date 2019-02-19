package org.ciaranroche.kang.adapters

import org.ciaranroche.kang.models.genre.GenreModel

interface GenreListener {
    fun onGenreClick(genre: GenreModel)
}