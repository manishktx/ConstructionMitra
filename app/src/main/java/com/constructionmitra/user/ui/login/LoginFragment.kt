package com.constructionmitra.user.ui.login

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.constructionmitra.user.R
import com.constructionmitra.user.databinding.FragmentLoginBinding
import com.constructionmitra.user.utilities.showToast

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private var profileType: ProfileType? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            tvHeading.text = HtmlCompat.fromHtml(getString(R.string.login_title), HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvHeading2.text = HtmlCompat.fromHtml(getString(R.string.labour_contractor), HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvLogin.setOnClickListener{
                profileType?.let {
                    LoginFragmentDirections.toRegistrationFragment(it).apply {
                        findNavController().navigate(this)
                    }
                    return@setOnClickListener
                }
                binding.root.showToast(getString(R.string.msg_select_your_category))
            }

            nirmaanKartaView.setOnClickListener {
                profileType = ProfileType.NIRMAAN_KARTA
                ivTickNirmaanKarta.visibility = View.VISIBLE
                ivTickNirmaanShramik.visibility = View.GONE
            }

            nirmaanShramikView.setOnClickListener {
                profileType = ProfileType.NIRMAAN_SHRAMIK
                ivTickNirmaanKarta.visibility = View.GONE
                ivTickNirmaanShramik.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}