package com.constructionmitra.user.ui.employer.engineer_supervisor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.get
import com.constructionmitra.user.R
import com.constructionmitra.user.data.EngineerEducation
import com.constructionmitra.user.databinding.FragmentEngineerAddGraduationDiplomaBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.utilities.showToast
import kotlinx.android.synthetic.main.fragment_engineer_add_graduation_diploma.*
import kotlinx.android.synthetic.main.fragment_engineer_home.*
import timber.log.Timber


class EngineerAddGraduationDiplomaFragment : Fragment() {

    private var _binding :  FragmentEngineerAddGraduationDiplomaBinding ?=null
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

        _binding= FragmentEngineerAddGraduationDiplomaBinding.inflate(inflater,container,false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener(View.OnClickListener {
            getData()?.let{
                Timber.d("Data = $it")
            }
        })
    }

    private fun getData(): EngineerEducation?{
        val educationData= EngineerEducation()

        if(binding.edtUniversity.text!!.isEmpty()){
            binding.root.showToast("Please enter university name")
            return null
        }
        if(binding.edtPassingYr.text!!.isEmpty()){
            binding.root.showToast("Please enter passing year")
            return null
        }
        if(binding.edtPercent.text!!.isEmpty()){
            binding.root.showToast("Please enter passing percentage")
            return null
        }
        return educationData.apply {
            //education_id = binding.spnEducation.get
            university = binding.edtUniversity.text.toString()
            passing_year = binding.edtPassingYr.text.toString()
            result_percentage = binding.edtPercent.text.toString()
        }
    }
}