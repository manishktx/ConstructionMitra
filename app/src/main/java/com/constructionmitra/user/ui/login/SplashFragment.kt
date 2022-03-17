package com.constructionmitra.user.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.constructionmitra.user.EngineerEmpActivity
import com.constructionmitra.user.MainActivity
import com.constructionmitra.user.R
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.ui.contractor.EmployerMainActivity
import com.constructionmitra.user.utilities.constants.AppConstants
import com.constructionmitra.user.utilities.ext.app
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var hasConfigDetailsReceived: Boolean = false

    @Inject
    lateinit var appPreferences: AppPreferences

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        viewModel.getAppConfig()
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
            if(!hasConfigDetailsReceived)
                delay(1000)
            if(!hasConfigDetailsReceived){
                // save dummy data
                app().saveConfig()
            }
            else {
                app().saveConfig(viewModel.configData.value)
            }
            if (appPreferences.getUserId().toInt() == 0) {
                findNavController().navigate(SplashFragmentDirections.toLoginFragment())
            } else {
                navigateToHome()
            }
        }

        viewModel.configData.observe(viewLifecycleOwner){
            hasConfigDetailsReceived = true
        }
    }

    private fun navigateToHome() {
        if (appPreferences.getUserType() == AppConstants.USER_TYPE_PETTY_CONTRACTOR){
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }else if( appPreferences.getUserType() == AppConstants.USER_TYPE_ENGINEER){
            val intent = Intent(context, EngineerEmpActivity::class.java)
            startActivity(intent)
        }else{
            val intent = Intent(context, EmployerMainActivity::class.java)
            startActivity(intent)
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