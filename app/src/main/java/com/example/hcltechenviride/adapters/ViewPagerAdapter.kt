package com.example.hcltechenviride.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    // Lists to store fragments and their corresponding titles
    private val fragmentList = mutableListOf<Fragment>() // List to hold fragments
    private val titleList = mutableListOf<String>() // List to hold titles

    override fun getCount(): Int {
        // Return the total number of fragments
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        // Return the fragment at the specified position
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // Return the title of the fragment at the specified position
        return titleList[position]
    }

    fun addFragments(fragment: Fragment, title: String) {
        // Add a fragment and its title to the lists
        fragmentList.add(fragment)
        titleList.add(title)
    }
}
