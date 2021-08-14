package com.constructionmitra.user.ui.contractor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.constructionmitra.user.R
import com.constructionmitra.user.databinding.FragmentAddEmployeeDetailsBinding
import com.constructionmitra.user.databinding.FragmentJobRoleDetailsBinding
import com.constructionmitra.user.databinding.FragmentSelectJobBinding
import kotlinx.android.synthetic.main.fragment_otp.*

class AddEmployeeDetailsFragment : Fragment() {

    private var _binding: FragmentAddEmployeeDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEmployeeDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}