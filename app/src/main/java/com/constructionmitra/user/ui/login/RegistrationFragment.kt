package com.constructionmitra.user.ui.login

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.constructionmitra.user.R
import com.constructionmitra.user.databinding.FragmentRegistrationBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.utilities.ServerConstants
import com.constructionmitra.user.utilities.showSnackBarShort
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_registration.*

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private lateinit var progressBarBinding: ProgressBarBinding
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val args: RegistrationFragmentArgs by navArgs()

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
            tvCtaHint.text = HtmlCompat.fromHtml(getString(R.string.registration_cta_hint), HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvNext.setOnClickListener {
            if(validateDetails()) {
                showProgress(true)
                viewModel.requestOtp(binding.etMobileNum.text.toString(),
                    when(args.profileType){
                        ProfileType.NIRMAAN_SHRAMIK -> "3"
                        ProfileType.NIRMAAN_KARTA -> "4"
                    },
                    binding.etName.text.toString()
                )
//                RegistrationFragmentDirections.toOtpFragment().apply {
//                    findNavController().navigate(this)
//                }
            }
            else {
                 binding.root.showSnackBarShort(getString(R.string.enter_valid_details))
            }
        }

        registerObservers()
    }

    private fun registerObservers() {
        viewModel.loginResponse.observe(viewLifecycleOwner){
            showProgress(false)
            if(it.status.equals(ServerConstants.STATUS_SUCCESS, ignoreCase = true)){
                RegistrationFragmentDirections.toOtpFragment(
                    otp =  it.data.otp,
                    id = it.data.id,
                    mobile = etMobileNum.text.toString(),
                    profileType = args.profileType,
                    name = binding.etName.text.toString()
                ).apply {
                    findNavController().navigate(this)
                }
            }
        }

        onError()

    }

    private fun onError() {
        viewModel.errorMsg.observe(viewLifecycleOwner){
            showProgress(false)
            binding.root.showToast("You are already registered!")
        }
    }


    private fun validateDetails() =
        binding.etMobileNum.text.toString().trim().length == 10 || binding.etName.text.toString().trim().length >= 3


    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}