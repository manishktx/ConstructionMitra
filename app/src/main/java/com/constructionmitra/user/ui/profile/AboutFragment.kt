package com.constructionmitra.user.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.ProfileData
import com.constructionmitra.user.databinding.FragmentAboutBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding
    private lateinit var progressBarBinding: ProgressBarBinding
    @Inject
    lateinit var appPreferences: AppPreferences

    private var isProfileChanged = false

    private val viewModel: ProfileViewModel by viewModels()

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
        binding = FragmentAboutBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgress(true)
        viewModel.fetchProfileInfo(appPreferences.getUserId()!!, appPreferences.getToken()!!)
        viewModel.profileData.observe(viewLifecycleOwner){
            showProgress(false)
            it?.let {
                bindData(it)
            }
        }

        // set listener
        binding.tvSaveDetails.setOnClickListener {
            if(!binding.etAddress.text?.trim().isNullOrEmpty() ||
                !binding.etCurrentLocation.text?.trim().isNullOrEmpty()){
                showProgress(true)
                viewModel.updateAddress(
                    appPreferences.getToken()!!,
                    appPreferences.getUserId()!!,
                    binding.etAddress.text.toString(),
                    binding.etCurrentLocation.text.toString())
            }
            else
                binding.root.showToast("Nothing to update!")
        }

        registerObservers()
    }

    private fun registerObservers() {
        viewModel.profileUpdated.observe(viewLifecycleOwner){
            showProgress(false)
            it?.let {
                if(it){
                    binding.root.showToast("ProfileUpdated!")
                }
            }
        }
    }

    private fun bindData(profileData: ProfileData) {
        with(binding){
            binding.profileData = profileData
        }
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            AboutFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}