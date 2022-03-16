package com.constructionmitra.user.ui.contractor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.constructionmitra.user.R
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.databinding.FragmentContractorHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_profile_card_2.*
import javax.inject.Inject

@AndroidEntryPoint
class ContractorHomeFragment : Fragment() {

    private var _binding: FragmentContractorHomeBinding? = null

    private val binding get() = _binding!!
    @Inject lateinit var appPreferences: AppPreferences

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
            tvName.text = appPreferences.getUserName()
            tvPhoneNum.text = appPreferences.getMobileNumber()
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