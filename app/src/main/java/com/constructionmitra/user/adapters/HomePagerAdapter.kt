package com.constructionmitra.user.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.constructionmitra.user.ui.home.HomeFragment

const val MY_PAGE_INDEX = 0
const val PLANT_LIST_PAGE_INDEX = 1

class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        MY_PAGE_INDEX to { HomeFragment() },
        PLANT_LIST_PAGE_INDEX to { HomeFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}
