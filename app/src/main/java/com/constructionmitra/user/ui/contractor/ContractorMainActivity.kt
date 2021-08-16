package com.constructionmitra.user.ui.contractor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.constructionmitra.user.R
import com.constructionmitra.user.api.CMitraService
import com.constructionmitra.user.databinding.ActivityContractorMainBinding
import com.constructionmitra.user.databinding.ActivityLoginBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_contractor_main.*
import kotlinx.android.synthetic.main.item_profile.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ContractorMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContractorMainBinding

    @Inject lateinit var api: CMitraService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContractorMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            tvTitle.text = HtmlCompat.fromHtml(getString(R.string.login_title), HtmlCompat.FROM_HTML_MODE_LEGACY)
            tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })
        }
    }

    companion object {
        fun log(message: String){
            Timber.d("ConstructionMitraService =  $message")
        }
    }

}