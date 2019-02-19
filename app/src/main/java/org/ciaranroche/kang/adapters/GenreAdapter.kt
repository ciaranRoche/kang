package org.ciaranroche.kang.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_vinyl.view.*
import org.ciaranroche.kang.R
import org.ciaranroche.kang.listeners.GenreListener
import org.ciaranroche.kang.models.genre.GenreModel
import org.jetbrains.anko.backgroundColorResource

class GenreAdapter constructor(private var genres: List<GenreModel>, private val listener: GenreListener) : RecyclerView.Adapter<GenreAdapter.MainHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreAdapter.MainHolder {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_vinyl, parent, false))
    }

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: GenreAdapter.MainHolder, position: Int) {
        val genre = genres[holder.adapterPosition]
        holder.bind(genre, listener)
    }

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(genre: GenreModel, listener: GenreListener){
            itemView.backgroundColorResource = genre.color
            itemView.vinylGenre.text = genre.title
            itemView.setOnClickListener{ listener.onGenreClick(genre) }
        }
    }
}