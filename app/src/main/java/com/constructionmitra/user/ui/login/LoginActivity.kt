package com.constructionmitra.user.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.constructionmitra.user.api.CMitraService
import com.constructionmitra.user.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    @Inject lateinit var api: CMitraService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        log("$api")
    }

    companion object {
        fun log(message: String){
            Timber.d("ConstructionMitraService =  $message")
        }
    }

}