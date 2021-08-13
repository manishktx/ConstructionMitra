package com.constructionmitra.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.constructionmitra.user.data.Job
import com.constructionmitra.user.databinding.FragmentContainerActivityBinding
import com.constructionmitra.user.factories.FragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentContainerActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentContainerActivityBinding.inflate(layoutInflater).also {
            this@FragmentContainerActivity.setContentView(it.root)

            val job = intent.getParcelableExtra<Job>(PARCELABLE_KEY)

            intent.getStringExtra(FRAGMENT_NAME)?.let { // check if name is not null
                supportFragmentManager.commit { // add fragment by commit
                    setReorderingAllowed(true)
                    add(
                        R.id.fragment_container,
                        FragmentFactory.create(it).apply {
                            job?.let {
                                // check if intent has extra parcelable {@Job} or not?
                                // if yes pass it to fragment
                                arguments?.apply {
                                    putParcelable(PARCELABLE_KEY, job)
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    companion object{
        const val  FRAGMENT_NAME = "key_fragment"
        const val  PARCELABLE_KEY = "key_parcelable"
    }
}