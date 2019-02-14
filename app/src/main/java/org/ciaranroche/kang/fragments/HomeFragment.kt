package org.ciaranroche.kang.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.nshmura.recyclertablayout.RecyclerTabLayout
import org.ciaranroche.kang.R
import org.ciaranroche.kang.adapters.VinylPagerAdapter
import org.ciaranroche.kang.models.VinylJSONStore
import org.ciaranroche.kang.models.VinylModel

class HomeFragment : Fragment(){
    lateinit var vinyls: MutableList<VinylModel>
    lateinit var viewPager: ViewPager
    lateinit var pagerAdapter: VinylPagerAdapter
    lateinit var data: VinylJSONStore
    private lateinit var recyclerTabLayout: RecyclerTabLayout


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_vinyl_list, container, false)

        data = VinylJSONStore(this.context!!.applicationContext)
        data.seed()
        vinyls = data.findAll()

        viewPager = view.findViewById(R.id.viewPager)
        pagerAdapter = VinylPagerAdapter(this.fragmentManager!!, vinyls as ArrayList<VinylModel>)

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
