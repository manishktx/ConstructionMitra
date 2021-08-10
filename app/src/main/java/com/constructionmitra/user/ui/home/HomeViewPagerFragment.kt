package com.constructionmitra.user.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.constructionmitra.user.R
import com.constructionmitra.user.adapters.HomePagerAdapter
import com.constructionmitra.user.adapters.MY_PAGE_INDEX
import com.constructionmitra.user.adapters.PLANT_LIST_PAGE_INDEX
import com.constructionmitra.user.databinding.FragmentHomeViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentHomeViewPagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = HomePagerAdapter(this)

        // set tabs indicator
        with(binding){
            TabLayoutMediator(tabs, viewPager) {
                    tab, position ->
//                tab.setIcon(getTabIcon(position))
                tab.text = getTabTitle(position)
            }.attach()

        }
    }

//    private fun getTabIcon(position: Int): Int {
//        return when (position) {
//            MY_PAGE_INDEX -> R.drawable.garden_tab_selector
//            PLANT_LIST_PAGE_INDEX -> R.drawable.plant_list_tab_selector
//            else -> throw IndexOutOfBoundsException()
//        }
//    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MY_PAGE_INDEX -> "Work Available"
            PLANT_LIST_PAGE_INDEX -> "Work Applied"
            else -> null
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeViewPagerFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}