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
import kotlinx.android.synthetic.main.card_vinyl.view.*
import kotlinx.android.synthetic.main.fragment_welcome.*
import org.ciaranroche.kang.R
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
class WelcomeFragment : Fragment() {
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
        recyclerView.adapter = GenreAdapter(app.genreList)

        return view
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

class GenreAdapter constructor(private var genres: List<String>) : RecyclerView.Adapter<GenreAdapter.MainHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreAdapter.MainHolder {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_vinyl, parent, false))
    }

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: GenreAdapter.MainHolder, position: Int) {
        val genre = genres[holder.adapterPosition]
        holder.bind(genre)
    }

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(genre: String){
            itemView.vinylGenre.text = genre
        }
    }
}
