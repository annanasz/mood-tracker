package hu.bme.aut.android.mood.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import hu.bme.aut.android.mood.fragment.CalendarFragment
import hu.bme.aut.android.mood.fragment.ChartsFragment
import hu.bme.aut.android.mood.fragment.EntriesFragment
import hu.bme.aut.android.mood.fragment.GoalsFragment

class MainPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {

    companion object {
        private const val NUM_PAGES = 4
    }

    override fun getCount(): Int = NUM_PAGES

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> EntriesFragment()
            1 -> CalendarFragment()
            2 -> ChartsFragment()
            3-> GoalsFragment()
            else -> throw IllegalArgumentException("No such page!")
        }
    }
}