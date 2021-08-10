package com.constructionmitra.user.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.constructionmitra.user.R
import com.constructionmitra.user.databinding.FragmentAboutBinding
import com.constructionmitra.user.databinding.FragmentAboutWorkExpBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class WorkPriorityFragment : Fragment() {

    private lateinit var binding: FragmentAboutWorkExpBinding
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutWorkExpBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            WorkPriorityFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}