package com.constructionmitra.user.ui.employer.engineer_supervisor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.constructionmitra.user.R
import com.constructionmitra.user.data.EngineerEducation
import com.constructionmitra.user.data.EngineerPersonDetails
import com.constructionmitra.user.databinding.FragmentEngineerAddTwelthDetailBinding
import com.constructionmitra.user.databinding.FragmentEngineerPersonalDetailBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.utilities.showToast
import kotlinx.android.synthetic.main.fragment_engineer_home.*
import kotlinx.android.synthetic.main.fragment_engineer_personal_detail.*
import timber.log.Timber


class EngineerPersonalDetailFragment : Fragment() {

    private var _binding : FragmentEngineerPersonalDetailBinding?=null
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
        _binding = FragmentEngineerPersonalDetailBinding.inflate(inflater,container,false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
        }
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener(View.OnClickListener {
            getData()?.let{
                Timber.d("Data = $it")
            }
        })
    }

    private fun getData(): EngineerPersonDetails?{
        val personalData= EngineerPersonDetails()

        if(binding.edtFullName.text!!.isEmpty()){
            binding.root.showToast("Please enter full name")
            return null
        }
        if(binding.edtDOB.text!!.isEmpty()){
            binding.root.showToast("Please enter date of birth")
            return null
        }
        if(binding.edtCurrentAddress.text!!.isEmpty()){
            binding.root.showToast("Please current address")
            return null
        }
        if(binding.edtPermanentAddress.text!!.isEmpty()){
            binding.root.showToast("Please enter permanent address")
            return null
        }
        if(binding.edtLanguage.text!!.isEmpty()){
            binding.root.showToast("Please enter date of birth")
            return null
        }
        if(binding.edtEmail.text!!.isEmpty()){
            binding.root.showToast("Please enter valid email address")
            return null
        }
        return personalData.apply {
            full_name = edtName.text.toString()
            dob  = edtDOB.text.toString()
            current_residence = edtCurrentAddress.text.toString()
            permanenet_residence  = edtPermanentAddress.text.toString()
            language_known  = edtLanguage.text.toString()
            email  = edtEmail.text.toString()
        }
    }
}