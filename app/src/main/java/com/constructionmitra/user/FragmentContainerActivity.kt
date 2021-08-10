package com.constructionmitra.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.constructionmitra.user.databinding.FragmentContainerActivityBinding
import com.constructionmitra.user.factories.FragmentFactory
import javax.inject.Inject

class FragmentContainerActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentContainerActivityBinding.inflate(layoutInflater).also {
            this@FragmentContainerActivity.setContentView(it.root)

            intent.getStringExtra(FRAGMENT_NAME)?.let {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add(
                        R.id.fragment_container,
                        FragmentFactory.create(it)
                    )
                }
            }
        }
    }

    companion object{
        const val  KEY_FRAGMENT = "key_fragment"
        const val  FRAGMENT_NAME = "key_fragment"
    }
}