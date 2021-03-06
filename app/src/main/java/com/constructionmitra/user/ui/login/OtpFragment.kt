package com.constructionmitra.user.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.constructionmitra.user.R
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.databinding.FragmentOtpBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.contractor.EmployerMainActivity
import com.constructionmitra.user.utilities.AppUtils
import com.constructionmitra.user.utilities.ServerConstants
import com.constructionmitra.user.utilities.showSnackBarShort
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_registration.*
import javax.inject.Inject

@AndroidEntryPoint
class OtpFragment : Fragment() {

    private lateinit var binding: FragmentOtpBinding
    private lateinit var progressBarBinding: ProgressBarBinding

    @Inject lateinit var appPreferences: AppPreferences

    private val args: OtpFragmentArgs by navArgs()

    private val viewModel: LoginViewModel by viewModels()

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
        binding = FragmentOtpBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
            tvResendOtp.setOnClickListener {
                showProgress(true)
                viewModel.requestOtp(
                    args.mobile,
                    when(args.profileType){
                        ProfileType.NIRMAAN_SHRAMIK -> "3"
                        ProfileType.NIRMAAN_KARTA -> "4"
                    },
                    name = args.name
                )
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etOtp.setText(args.otp)
        binding.tvNext.setOnClickListener {
            if(validateOtp()){
                showProgress(true)
                viewModel.verifyOtp(
                    mobile =  args.mobile,
                    otp = binding.etOtp.text.toString()
                )
            }else{
                binding.root.showSnackBarShort(getString(R.string.enter_valid_details))
            }
//            OtpFragmentDirections.toWorkCategory().apply {
//                findNavController().navigate(this)
//            }
        }
        registerObservers()
    }

    private fun registerObservers() {
        viewModel.loginResponse.observe(viewLifecycleOwner){
            showProgress(false)
            binding.etOtp.setText(it.data.otp)
        }

        viewModel.verifyOtpData.observe(viewLifecycleOwner){
            showProgress(false)
            it?.let {
                // Safe data to pref
                appPreferences.saveUserDetails(
                    it.userId, it.userRole, it.token, it.name, args.mobile
                )
                when(args.profileType)
                {
                    // navigate to work category Fragment
                    ProfileType.NIRMAAN_SHRAMIK -> {
                        OtpFragmentDirections.toWorkCategory(
                            args.mobile
                        ).apply {
                            findNavController().navigate(this)
                        }
                    }
                    // navigate to work Contractor profile
                    else -> navigateToContractorProfile()
                }

            } ?: binding.root.showSnackBarShort("Please try again")
        }

        viewModel.errorMsg.observe(viewLifecycleOwner){
            showProgress(false)
            it?.let {
                binding.root.showSnackBarShort(it)
            }
        }
    }

    private fun navigateToContractorProfile(){
        appPreferences.saveBoolean(AppPreferences.IS_NEW_CONTRACTOR, true)
        Intent(context, EmployerMainActivity::class.java).apply {
            requireContext().startActivity(this)
            requireActivity().finish()
        }
        requireActivity().overridePendingTransition(R.anim.enter_anim_activity, R.anim.exit_anim_activity)
    }

    private fun validateOtp() =
        binding.etOtp.text.toString().trim().length == 4

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OtpFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}