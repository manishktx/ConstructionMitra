package com.constructionmitra.user.ui.contractor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.constructionmitra.user.databinding.FragmentContractorHomeContainerBinding
import com.constructionmitra.user.ui.contractor.viewmodels.JobPostViewModel
import com.constructionmitra.user.ui.contractor.viewmodels.UiViewModel

private const val NUM_PAGES = 2
class EmployerHomeFragment : Fragment() {

    private var _binding: FragmentContractorHomeContainerBinding? = null
    private val viewModel: JobPostViewModel by lazy {
        ViewModelProvider(requireActivity()).get(JobPostViewModel::class.java)
    }
    private val uiViewModel: UiViewModel by lazy {
        ViewModelProvider(requireActivity()).get(UiViewModel::class.java)
    }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        uiViewModel.showHomeTabs(true)
        _binding = FragmentContractorHomeContainerBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            vpProfile.adapter = ScreenSlidePagerAdapter(childFragmentManager)
        }

        viewModel.tabSelected.observe(viewLifecycleOwner){
            binding.vpProfile.currentItem = it
        }

        uiViewModel.initJobPost.observe(viewLifecycleOwner){
            if(it){
                findNavController().navigate(
                    EmployerHomeFragmentDirections.toSelectJobFragment()
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        uiViewModel.showHomeTabs(false)
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = NUM_PAGES

        override fun getItem(position: Int): Fragment =
            if(position == 0) PostedJobsFragment() else  ContractorProfileFragment()
    }
}