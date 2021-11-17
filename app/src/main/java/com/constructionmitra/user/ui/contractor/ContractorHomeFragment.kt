package com.constructionmitra.user.ui.contractor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.constructionmitra.user.R
import com.constructionmitra.user.databinding.FragmentContractorHomeBinding

class ContractorHomeFragment : Fragment() {

    private var _binding: FragmentContractorHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContractorHomeBinding.inflate(inflater, container, false).apply {
            tvTitle.text = HtmlCompat.fromHtml(getString(R.string.title_contractor_home_screen), HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvPostAJob.setOnClickListener {
                ContractorHomeFragmentDirections.toSelectJobFragment().apply {
                    findNavController().navigate(this)
                }
            }
        }
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