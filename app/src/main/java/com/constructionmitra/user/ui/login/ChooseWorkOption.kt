package com.constructionmitra.user.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.constructionmitra.user.databinding.FragmentChooseWorkOptionBinding

class ChooseWorkOption : Fragment() {

    private lateinit var binding: FragmentChooseWorkOptionBinding

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
        binding = FragmentChooseWorkOptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChooseWorkOption().apply {
                arguments = Bundle().apply {

                }
            }
    }
}