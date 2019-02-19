package org.ciaranroche.kang.fragments

import android.os.Bundle
import android.util.Log
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


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [WelcomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [WelcomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
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
        Log.i("boop", genre)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WelcomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

