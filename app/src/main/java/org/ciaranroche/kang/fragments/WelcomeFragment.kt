package org.ciaranroche.kang.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.ciaranroche.kang.R
import org.ciaranroche.kang.adapters.GenreAdapter
import org.ciaranroche.kang.adapters.GenreListener
import org.ciaranroche.kang.main.MainApp


class WelcomeFragment : Fragment(), GenreListener {
    lateinit var mveBtn: Button
    lateinit var app : MainApp
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)
        mveBtn = view.findViewById(R.id.move_button)

        mveBtn.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_welcomeFragment_to_vinylProfileFragment)
        }

        app = this.context!!.applicationContext as MainApp

        val layoutManager = LinearLayoutManager(this.context)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = GenreAdapter(app.genreList, this)

        return view
    }

    override fun onGenreClick(genre: String) {
        view?.findNavController()?.navigate(R.id.action_welcomeFragment_to_vinylProfileFragment)
    }
}

