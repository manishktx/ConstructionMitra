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
            val saveImageWherePath = intent.getStringExtra(SAVE_WHERE)
            if(filePath.isNullOrEmpty() && saveImageWherePath.isNullOrEmpty())
                throw  Exception("File path or saveImageWherePath can not be null")

            supportFragmentManager.commit { // add fragment by commit
                setReorderingAllowed(true)
                add(
                    R.id.fragment_container,
                    PreviewImageFragment.newInstance(filePath!!, saveImageWherePath!!)
                )
            }
        }
    }
    companion object{
        const val  FILE_PATH = "file_path"
        const val  SAVE_WHERE = PreviewImageFragment.SAVE_WHERE
    }
}