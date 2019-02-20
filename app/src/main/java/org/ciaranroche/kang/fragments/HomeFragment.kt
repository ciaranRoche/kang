package org.ciaranroche.kang.fragments

import android.os.Bundle
import android.util.Log

import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager.widget.ViewPager
import com.nshmura.recyclertablayout.RecyclerTabLayout
import org.ciaranroche.kang.R
import org.ciaranroche.kang.adapters.VinylPagerAdapter
import org.ciaranroche.kang.main.MainApp


class HomeFragment : Fragment(){

    lateinit var viewPager: ViewPager
    lateinit var pagerAdapter: VinylPagerAdapter
    lateinit var recyclerTabLayout: RecyclerTabLayout
    lateinit var mveBtn: Button
    lateinit var app : MainApp

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val view : View

        app = this.context!!.applicationContext as MainApp

        if(app.vinylsList.size == 0){
            view = inflater.inflate(R.layout.fragment_no_vinyl, container, false)
            mveBtn = view.findViewById(R.id.move_button)

            mveBtn.setOnClickListener { view ->
                view.findNavController().navigate(R.id.action_vinylProfileFragment_to_add_vinyl)
            }
        } else {
            view = inflater.inflate(R.layout.fragment_vinyl_list, container, false)

            viewPager = view.findViewById(R.id.viewPager)
            pagerAdapter = VinylPagerAdapter(fragmentManager!!, app.vinylsList)

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
