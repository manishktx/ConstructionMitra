package com.constructionmitra.user.ui.contractor

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.constructionmitra.user.R
import com.constructionmitra.user.api.CMitraService
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.databinding.ActivityContractorMainBinding
import com.constructionmitra.user.databinding.ActivityLoginBinding
import com.constructionmitra.user.ui.contractor.viewmodels.JobPostViewModel
import com.constructionmitra.user.ui.contractor.viewmodels.UiViewModel
import com.constructionmitra.user.ui.dialogs.LogoutDialog
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_contractor_main.*
import kotlinx.android.synthetic.main.item_profile.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class EmployerMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContractorMainBinding

    @Inject lateinit var api: CMitraService
    @Inject lateinit var appPreferences: AppPreferences

    private val viewModel: JobPostViewModel by viewModels()
    private val uiViewModel: UiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init()
        binding = ActivityContractorMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            tvTitle.text = HtmlCompat.fromHtml(getString(R.string.app_title_english), HtmlCompat.FROM_HTML_MODE_LEGACY)

            // set destination for new or old user
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.contractorNavHost) as NavHostFragment
            val graph = navHostFragment.navController.navInflater.inflate(R.navigation.contractor_navigation)
            val destination = if(appPreferences.getBoolean(AppPreferences.IS_NEW_CONTRACTOR))
                R.id.contractorHomeFragment
            else {
                R.id.contractorHomeContainerFragment
            }
            graph.startDestination = destination

            navHostFragment.navController.graph = graph

            if(!appPreferences.getBoolean(AppPreferences.IS_NEW_CONTRACTOR)){
                uiViewModel.init()
                tabs.visibility = View.VISIBLE
            }

            tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab ?: return
                    viewModel.onTabSelected(tab.position)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })
            ivLogout.setOnClickListener {
                // Safe data to pref
                LogoutDialog.newInstance(context = this@EmployerMainActivity, {

                }, {
                    // Yes
                    appPreferences.saveUserDetails(
                        "0", "", ""
                    )
                    finish()
                }).show()
            }
        }

        registerObservers()
    }

    private fun registerObservers() {
        uiViewModel.showTabs.observe(this){
            binding.tabs.visibility = if(it) View.VISIBLE else View.GONE
        }
    }

    companion object {
        fun log(message: String){
            Timber.d("ConstructionMitraService =  $message")
        }
    }
}