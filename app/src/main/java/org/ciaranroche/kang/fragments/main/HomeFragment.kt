package org.ciaranroche.kang.fragments.main

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager.widget.ViewPager
import com.nshmura.recyclertablayout.RecyclerTabLayout
import org.ciaranroche.kang.R
import org.ciaranroche.kang.adapters.VinylPagerAdapter
import org.ciaranroche.kang.main.MainApp
import org.ciaranroche.kang.models.genre.GenreModel
import org.ciaranroche.kang.models.vinyl.VinylModel

class HomeFragment : Fragment() {

    lateinit var viewPager: ViewPager
    lateinit var pagerAdapter: VinylPagerAdapter
    lateinit var recyclerTabLayout: RecyclerTabLayout
    lateinit var mveBtn: Button
    lateinit var app: MainApp
    lateinit var genre: GenreModel
    lateinit var vinylList: ArrayList<VinylModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            vinylList = it.getParcelableArrayList("vinyl")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val view: View

        app = this.context!!.applicationContext as MainApp

        if (vinylList.size == 0) {
            view = inflater.inflate(R.layout.fragment_no_vinyl, container, false)
            mveBtn = view.findViewById(R.id.move_button)

            mveBtn.setOnClickListener { view ->
                view.findNavController().navigate(R.id.action_vinylProfileFragment_to_add_vinyl)
            }
        } else {
            view = inflater.inflate(R.layout.fragment_vinyl_list, container, false)

            vinylList.sortBy { it.name }

            viewPager = view.findViewById(R.id.viewPager)
            pagerAdapter = VinylPagerAdapter(fragmentManager!!, vinylList)

            viewPager.adapter = pagerAdapter
            viewPager.currentItem = pagerAdapter.count / 2

            recyclerTabLayout = view.findViewById(R.id.recyclerTabLayout)
            recyclerTabLayout.setUpWithViewPager(viewPager)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }
}
