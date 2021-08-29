package com.constructionmitra.user.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.constructionmitra.user.MainActivity
import com.constructionmitra.user.R
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.ui.contractor.ContractorMainActivity
import com.constructionmitra.user.utilities.constants.AppConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenResumed {
            delay(2000)
            if (appPreferences.getUserId().toInt() == 0) {
                findNavController().navigate(SplashFragmentDirections.toLoginFragment())
            } else {
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        Intent(context, if (appPreferences.getUserType() == AppConstants.USER_TYPE_PETTY_CONTRACTOR)
            MainActivity::class.java
        else
            ContractorMainActivity::class.java).apply {
            requireContext().startActivity(this)
            requireActivity().finish()
        }
        requireActivity().overridePendingTransition(R.anim.enter_anim_activity,
            R.anim.exit_anim_activity)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SplashFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}