package com.constructionmitra.user.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.constructionmitra.user.adapters.HomeAdapter
import com.constructionmitra.user.adapters.MY_PAGE_INDEX
import com.constructionmitra.user.adapters.PLANT_LIST_PAGE_INDEX
import com.constructionmitra.user.data.dummyWorkList
import com.constructionmitra.user.databinding.FragmentAvailableWorkListBinding
import com.constructionmitra.user.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class AvailableWorkListFragment : Fragment() {

    private var _binding: FragmentAvailableWorkListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAvailableWorkListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvWork.adapter = HomeAdapter(dummyWorkList){

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}