package com.constructionmitra.user.ui.employer.engineer_supervisor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.constructionmitra.user.R
import com.constructionmitra.user.databinding.FragmentEngineerAddPostGraduationBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import kotlinx.android.synthetic.main.fragment_engineer_home.*


class EngineerAddPostGraduationFragment : Fragment() {

    private var _binding : FragmentEngineerAddPostGraduationBinding?=null
    private lateinit var progressBarBinding: ProgressBarBinding
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding  = FragmentEngineerAddPostGraduationBinding.inflate(inflater,container,false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
        }
        return binding.root
    }
}