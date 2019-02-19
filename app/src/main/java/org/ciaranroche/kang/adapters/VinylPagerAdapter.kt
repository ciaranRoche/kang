package org.ciaranroche.kang.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import org.ciaranroche.kang.fragments.VinylProfileFragment
import org.ciaranroche.kang.models.vinyl.VinylModel

class VinylPagerAdapter(fragmentManager: FragmentManager, private val vinyls: ArrayList<VinylModel>) : FragmentStatePagerAdapter(fragmentManager) {

    private val MAX_VALUE = 200

    override fun getItem(position: Int): Fragment {
        return VinylProfileFragment.newInstance(vinyls[position % vinyls.size])
    }

    override fun getCount(): Int {
        return vinyls.size * MAX_VALUE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return vinyls[position % vinyls.size].name
    }

}