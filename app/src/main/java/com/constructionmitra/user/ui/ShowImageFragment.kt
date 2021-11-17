package com.constructionmitra.user.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.constructionmitra.user.R
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.databinding.FragmentPreviewImageBinding
import com.constructionmitra.user.databinding.FragmentShowImageBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.profile.AboutFragment
import com.constructionmitra.user.ui.profile.ProfileViewModel
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

class ShowImageFragment : Fragment() {

    private var imageUrl: String? = null

    private lateinit var binding: FragmentShowImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageUrl = it.getString(IMAGE_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentShowImageBinding.inflate(inflater, container, false).apply {

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageUrl?.let {
            Glide.with(binding.root).load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.pinchZoomImageView)
        }
    }

    companion object {
        const val  IMAGE_URL = "image_url"

        @JvmStatic
        fun newInstance() =
            ShowImageFragment().apply {
                    arguments = Bundle().apply {

                    }
            }
    }

}