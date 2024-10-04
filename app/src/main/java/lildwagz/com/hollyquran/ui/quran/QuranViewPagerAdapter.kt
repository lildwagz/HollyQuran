package com.mobiledevidn.quranku.ui.quran

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class QuranViewPagerAdapter(val fragment: Fragment, val fragmentList: List<Fragment>) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}