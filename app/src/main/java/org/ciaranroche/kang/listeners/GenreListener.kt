package org.ciaranroche.kang.listeners

import org.ciaranroche.kang.models.genre.GenreModel

interface GenreListener {
    fun onGenreClick(genre: GenreModel)
}