package org.ciaranroche.kang.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.ciaranroche.kang.R
import org.ciaranroche.kang.adapters.GenreAdapter
import org.ciaranroche.kang.listeners.GenreListener
import org.ciaranroche.kang.main.MainApp
import org.ciaranroche.kang.models.genre.GenreModel
import org.ciaranroche.kang.models.vinyl.VinylModel

class WelcomeFragment : Fragment(), GenreListener {
    lateinit var app: MainApp
    lateinit var recyclerView: RecyclerView
    lateinit var vinylList: ArrayList<VinylModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)

        app = this.context!!.applicationContext as MainApp

        val layoutManager = LinearLayoutManager(this.context)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = GenreAdapter(app.genreList, this)

        return view
    }

    override fun onGenreClick(genre: GenreModel) {
        if (genre.title != "All") {
            vinylList = ArrayList()
            app.vinylsList.forEach { if (it.genre.title == genre.title) vinylList.add(it) }
        } else {
            vinylList = app.vinylsList
        }
        val bundle = Bundle()
        bundle.putParcelableArrayList("vinyl", vinylList)
        view?.findNavController()?.navigate(R.id.action_welcomeFragment_to_vinylProfileFragment, bundle)
    }
}
