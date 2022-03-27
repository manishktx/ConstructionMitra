package com.constructionmitra.user.ui.employer.engineer_supervisor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.constructionmitra.user.R
import com.constructionmitra.user.data.EngineerEducation
import com.constructionmitra.user.databinding.FragmentEngineerAddTwelthDetailBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.utilities.showToast
import kotlinx.android.synthetic.main.fragment_engineer_home.*
import timber.log.Timber


class EngineerAddTwelthDetailFragment : Fragment() {

    private var _binding : FragmentEngineerAddTwelthDetailBinding?=null
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
        _binding = FragmentEngineerAddTwelthDetailBinding.inflate(inflater,container,false).apply {
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

    private fun getData(): EngineerEducation?{
        val educationData= EngineerEducation()

        if(binding.edtPassingYr.text!!.isEmpty()){
            binding.root.showToast("Please enter passing year")
            return null
        }
        if(binding.edtPassPercent.text!!.isEmpty()){
            binding.root.showToast("Please enter passing percentage")
            return null
        }
        return educationData.apply {
            passing_year = binding.edtPassingYr.text.toString()
            result_percentage = binding.edtPassPercent.text.toString()
        }
    }
}