package com.constructionmitra.user.ui.employer.engineer_supervisor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.constructionmitra.user.R
import com.constructionmitra.user.databinding.*
import kotlinx.android.synthetic.main.fragment_engineer_home.*


class EngineerUploadResumeFragment : Fragment() {

    private var _binding: FragmentEngineerUploadResumeBinding?=null
    private lateinit var progressBarBinding: ProgressBarBinding
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            _binding = FragmentEngineerUploadResumeBinding.inflate(inflater, container, false).apply {
                progressBarBinding = ProgressBarBinding.bind(root)
            }
            return binding.root
    }


}