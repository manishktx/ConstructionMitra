package com.constructionmitra.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.constructionmitra.user.data.Job
import com.constructionmitra.user.databinding.FragmentContainerActivityBinding
import com.constructionmitra.user.factories.FragmentFactory
import com.constructionmitra.user.ui.ShowImageFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
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
            val path = intent.getStringExtra(PATH)
            val imageUrl = intent.getStringExtra(IMAGE_URL)

            intent.getStringExtra(FRAGMENT_NAME)?.let { // check if name is not null
                supportFragmentManager.commit { // add fragment by commit
                    setReorderingAllowed(true)
                    add(
                        R.id.fragment_container,
                        FragmentFactory.create(it).apply {
                            // check if intent has extra parcelable {@Job} or not?
                            // if yes pass it to fragment
                            job?.let {
                                arguments?.apply {
                                    putParcelable(PARCELABLE_KEY, job)
                                    putString(PATH, path)
                                }
                            }
                            // Pass image url to fragment as argument
                            imageUrl?.let {
                                arguments?.apply {
                                    Timber.d("Image added")
                                    putString(ShowImageFragment.IMAGE_URL, it)
                                }
                            }
                            path?.let {
                                arguments?.apply {
                                    Timber.d("Path added")
                                    putString(PATH, it)
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
        const val  PATH = "from"
        const val  IMAGE_URL = "image_url"
        const val  PARCELABLE_KEY = "key_parcelable"
    }
}