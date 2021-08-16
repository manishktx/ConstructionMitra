package com.constructionmitra.user.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.constructionmitra.user.adapters.LocationAdapter
import com.constructionmitra.user.api.ProfileRequests
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.databinding.FragmentAboutYourWorkLocationBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.utilities.StringUtils
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class WorkLocationFragment : Fragment() {

    private lateinit var binding: FragmentAboutYourWorkLocationBinding
    private lateinit var progressBarBinding: ProgressBarBinding
    private var isUpdated: Boolean = false

    @Inject
    lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var profileRequests: ProfileRequests

    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if(isUpdated)
                requireActivity().setResult(AppCompatActivity.RESULT_OK)
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutYourWorkLocationBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)
        showProgress(true)
        // Get and observe all active work locations
        viewModel.getActiveLocations()
        viewModel.activeLocations.observe(viewLifecycleOwner){
            showProgress(false)
            it?.let {
                binding.tvSave.visibility = View.VISIBLE
                binding.rvWorkLocations.adapter = LocationAdapter(it){

                }
                return@observe
            }
        }

        binding.tvSave.setOnClickListener {
            viewModel.activeLocations.value?.let {
                _locations ->
                val locationIds = StringUtils.stringPresentationOfLocations(_locations)
                showProgress(true)
                viewModel.updateProfile(profileRequests.updateLocation(
                    userId = appPreferences.getUserId()!!,
                    token = appPreferences.getToken()!!,
                    preferredWorkLocations = locationIds
                ))
            }
        }

        registerObserver()

    }

    private fun registerObserver(){
        viewModel.profileUpdated.observe(viewLifecycleOwner){
            showProgress(false)
            if(it){
                isUpdated = true
                binding.root.showToast("Your work preferences updated!")
            }
        }
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            WorkLocationFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}