package com.constructionmitra.user.ui.contractor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.constructionmitra.user.R
import com.constructionmitra.user.databinding.FragmentSelectJobBinding
import com.constructionmitra.user.databinding.FragmentSelectWorkBinding
import kotlinx.android.synthetic.main.fragment_otp.*

class SelectWorkFragment : Fragment() {

    private var _binding: FragmentSelectWorkBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectWorkBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
//            val items = listOf("Material", "Design", "Components", "Android")
            val items = resources.getStringArray(R.array.job_categories)
            val adapter = ArrayAdapter(requireContext(), R.layout.item_drop_down, items)
            (textInput.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            tvContinue.setOnClickListener {
                SelectWorkFragmentDirections.toJobDetailsFragment().apply {
                    findNavController().navigate(this)
                }
            }

            // job roles layout
            addJobRolesLayout.layoutJobRolesContainer.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}