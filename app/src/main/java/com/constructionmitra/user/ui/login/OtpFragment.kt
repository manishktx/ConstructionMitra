package com.constructionmitra.user.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.constructionmitra.user.R
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.databinding.FragmentOtpBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.utilities.showSnackBarShort
import dagger.hilt.android.AndroidEntryPoint
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
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        viewModel.verifyOtpData.observe(viewLifecycleOwner){
            showProgress(false)
            it?.let {
                // Safe data to pref
                appPreferences.saveUserDetails(
                    it.userId, it.userRole, it.token
                )
                // navigate to work category Fragment
                OtpFragmentDirections.toWorkCategory(
                    args.mobile
                ).apply {
                    findNavController().navigate(this)
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