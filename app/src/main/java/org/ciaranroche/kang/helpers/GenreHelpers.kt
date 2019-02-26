package org.ciaranroche.kang.helpers

import org.ciaranroche.kang.R
import org.ciaranroche.kang.models.genre.GenreModel

fun seedGenres(): ArrayList<GenreModel> {
    val genres = ArrayList<GenreModel>()
    genres.add(GenreModel("All", "Doom metal is an extreme subgenre of heavy metal music that typically uses slower tempos, low-tuned guitars and a much thicker or heavier sound than other heavy metal genres. ", R.color.colorPrimaryDark))
    genres.add(GenreModel("Traditional", "Influenced by 70s and 80s heavy metal, traditional doom metal bands more commonly use higher guitar tunings and do not play as slow as many other doom bands.", R.color.colorAccent))
    genres.add(GenreModel("Black", "Is a style that combines the slowness and thicker, bassier sound of doom metal with the shrieking vocals and heavily distorted guitar sound of black metal.", R.color.colorPrimaryDark))
    genres.add(GenreModel("Death", "It combines the slow tempos and pessimistic or depressive mood of doom metal with the deep growling vocals and double kick drumming of death metal.", R.color.colorAccent))
    genres.add(GenreModel("Funeral", "Funeral doom is a genre that crosses death-doom with funeral dirge music. It is played at a very slow tempo, and places an emphasis on evoking a sense of emptiness and despair.", R.color.colorPrimaryDark))
    genres.add(GenreModel("Drone", "Is a style of heavy metal that melds the slow tempos and heaviness of doom metal with the long-duration tones of drone music the electric guitar is performed with a large amount of reverb or audio feedback", R.color.colorAccent))
    genres.add(GenreModel("Gothic", "Gothic-doom, also known as doom-gothic, is a style that combines more traditional elements of doom metal with gothic rock usually played at slow and mid-tempos", R.color.colorPrimaryDark))
    genres.add(GenreModel("Progressive", "Progressive doom is a fusion genre that combines elements of progressive metal and doom metal.", R.color.colorAccent))
    genres.add(GenreModel("Sludge", "Is a style that combines doom metal and hardcore punk, and sometimes Southern rock compose slow and heavy songs that contain brief hardcore passages.", R.color.colorPrimaryDark))
    genres.add(GenreModel("Stoner", "Describes doom metal that incorporates psychedelic rock and acid rock elements  is often heavily distorted, groove-laden bass-heavy sound, making much use of guitar effects such as fuzz, phaser, or flanger.", R.color.colorAccent))
    return genres
}