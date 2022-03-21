package com.constructionmitra.user.ui.employer.engineer_supervisor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.NavHostFragment
import com.constructionmitra.user.R
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.databinding.ActivityEngineerMainBinding
import com.constructionmitra.user.ui.contractor.viewmodels.JobPostViewModel
import com.constructionmitra.user.ui.contractor.viewmodels.UiViewModel
import com.constructionmitra.user.ui.dialogs.LogoutDialog
import com.google.android.material.tabs.TabLayout
import javax.inject.Inject

class EngineerMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEngineerMainBinding
    @Inject lateinit var appPreferences: AppPreferences

    private val viewModel: JobPostViewModel by viewModels()
    private val uiViewModel: UiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEngineerMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            tvTitle.text = HtmlCompat.fromHtml(getString(R.string.app_title_english_engineer), HtmlCompat.FROM_HTML_MODE_LEGACY)

            // set destination for new or old user
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.engineerNavHost) as NavHostFragment
            val graph = navHostFragment.navController.navInflater.inflate(R.navigation.engineer_navigation)

            /*val destination = if(appPreferences.getBoolean(AppPreferences.IS_NEW_CONTRACTOR))
                R.id.engineerHomeFragment
            else {
                R.id.engineerHomeFragment
            }*/

            graph.startDestination = R.id.engineerExperianceDetail

            navHostFragment.navController.graph = graph

//            if(!appPreferences.getBoolean(AppPreferences.IS_NEW_CONTRACTOR)){
                uiViewModel.init()

  //          }

            ivLogout.setOnClickListener {
                // Safe data to pref
                LogoutDialog.newInstance(context = this@EngineerMainActivity, {

                }, {
                    //appPreferences.saveUserDetails(
                    //    "0", "", ""
                    //)
                    finish()
                }).show()
            }
        }


    }
}