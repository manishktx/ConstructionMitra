package com.constructionmitra.user.ui.employer.engineer_supervisor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.constructionmitra.user.R
import com.constructionmitra.user.data.EngineerEducation
import com.constructionmitra.user.databinding.FragmentEngineerAddTenthDetailBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.utilities.showToast
import kotlinx.android.synthetic.main.fragment_engineer_add_twelth_detail.*
import kotlinx.android.synthetic.main.fragment_engineer_home.*
import timber.log.Timber


class EngineerAddTenthDetailFragment : Fragment() {

    private var _binding : FragmentEngineerAddTenthDetailBinding?=null
    private lateinit var progressBarBinding: ProgressBarBinding
    private  val binding get() = _binding!!

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
        _binding  = FragmentEngineerAddTenthDetailBinding.inflate(inflater,container,false).apply {
            progressBarBinding  = ProgressBarBinding.bind(root)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSave.setOnClickListener(View.OnClickListener {
            getData()?.let{
                Timber.d("Data = $it")
            }
        })
    }

    private fun getData(): EngineerEducation?{
        val educationData = EngineerEducation()

        if(binding.txtPassYr.text!!.isEmpty()){
            binding.root.showToast("Enter Passing Year")
            return null
        }
        if(binding.txtPassPerc.text!!.isEmpty()){
            binding.root.showToast("Enter Pass Percentage")
            return null
        }

        return educationData.apply {
            passing_year = binding.edtPassingYr.text.toString()
            result_percentage  = binding.edtPassPercent.text.toString()
        }
    }
}