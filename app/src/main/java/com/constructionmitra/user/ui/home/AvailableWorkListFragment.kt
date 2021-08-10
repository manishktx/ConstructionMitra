package com.constructionmitra.user.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.constructionmitra.user.FragmentContainerActivity
import com.constructionmitra.user.R
import com.constructionmitra.user.adapters.HomeAdapter
import com.constructionmitra.user.adapters.MY_PAGE_INDEX
import com.constructionmitra.user.adapters.PLANT_LIST_PAGE_INDEX
import com.constructionmitra.user.data.dummyWorkList
import com.constructionmitra.user.databinding.FragmentAvailableWorkListBinding
import com.constructionmitra.user.databinding.FragmentHomeBinding
import com.constructionmitra.user.ui.work.WorkDetailsFragment
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
            Intent(requireContext(), FragmentContainerActivity::class.java).apply {
                putExtra(FragmentContainerActivity.FRAGMENT_NAME, WorkDetailsFragment::class.java.name)
                startActivity(this)
            }
            requireActivity().overridePendingTransition(
                R.anim.enter_anim_activity,
                R.anim.exit_anim_activity
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}