package com.constructionmitra.user.ui.contractor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.ProfileData
import com.constructionmitra.user.databinding.FragmentContractorProfileBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.contractor.viewmodels.ContractorProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ContractorProfileFragment : Fragment() {

    private var _binding: FragmentContractorProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressBarBinding: ProgressBarBinding

    private val viewModel: ContractorProfileViewModel by viewModels()
    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContractorProfileBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            showProgress(true)
            viewModel.fetchProfileInfo(
                appPreferences.getUserId(),
                appPreferences.getToken()!!
            )
        }
    }

    private fun registerObservers(){
        viewModel.profileData.observe(viewLifecycleOwner){
            showProgress(false)
            it?.let {
                bindData(it)
            }
        }
    }

    private fun bindData(profileData: ProfileData) {
        with(binding){
            etEmployeeName.setText(profileData.fullName)
            etMobileNum.setText(profileData.phoneNumber)
        }
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}