package org.ciaranroche.kang.fragments

import android.os.Bundle
import android.provider.Contacts
import android.provider.Contacts.Intents.UI

import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.nshmura.recyclertablayout.RecyclerTabLayout
import org.ciaranroche.kang.R
import org.ciaranroche.kang.adapters.VinylPagerAdapter
import org.ciaranroche.kang.main.MainApp
import org.ciaranroche.kang.models.VinylFireStore
import org.ciaranroche.kang.models.VinylModel
import org.ciaranroche.kang.models.generateRandomId
import org.jetbrains.anko.doAsync


class HomeFragment : Fragment(){

    lateinit var viewPager: ViewPager
    lateinit var pagerAdapter: VinylPagerAdapter
    lateinit var recyclerTabLayout: RecyclerTabLayout
    lateinit var app : MainApp

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_vinyl_list, container, false)

        app = this.context!!.applicationContext as MainApp

        viewPager = view.findViewById(R.id.viewPager)
        pagerAdapter = VinylPagerAdapter(fragmentManager!!, app.vinylsList)

        viewPager.adapter = pagerAdapter
        viewPager.currentItem = pagerAdapter.count / 2

        recyclerTabLayout = view.findViewById(R.id.recyclerTabLayout)
        recyclerTabLayout.setUpWithViewPager(viewPager)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

}
