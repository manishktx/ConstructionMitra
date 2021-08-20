package com.constructionmitra.user.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.constructionmitra.user.R
import com.constructionmitra.user.databinding.FragmentContainerActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class PreviewImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentContainerActivityBinding.inflate(layoutInflater).also {
            this@PreviewImageActivity.setContentView(it.root)
            val filePath = intent.getStringExtra(FILE_PATH)
            if(filePath.isNullOrEmpty())
                throw  Exception("File path can not be null")

            supportFragmentManager.commit { // add fragment by commit
                setReorderingAllowed(true)
                add(
                    R.id.fragment_container,
                    PreviewImageFragment.newInstance(filePath)
                )
            }
        }
    }
    companion object{
        const val  FILE_PATH = "file_path"
    }
}