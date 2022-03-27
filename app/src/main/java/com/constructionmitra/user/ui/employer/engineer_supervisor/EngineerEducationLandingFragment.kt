package com.constructionmitra.user.ui.employer.engineer_supervisor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.constructionmitra.user.R
import com.constructionmitra.user.databinding.FragmentAboutBinding
import com.constructionmitra.user.databinding.FragmentEngineerEducationLandingBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.login.SplashFragmentDirections
import com.constructionmitra.user.utilities.ext.setDestination
import kotlinx.android.synthetic.main.fragment_engineer_home.*


class EngineerEducationLandingFragment : Fragment() {

    private lateinit var binding: FragmentEngineerEducationLandingBinding
    private lateinit var progressBarBinding: ProgressBarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }
    }

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
//            if(isProfileUpdated)
//                requireActivity().setResult(AppCompatActivity.RESULT_OK)
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback
        )
        binding =
            FragmentEngineerEducationLandingBinding.inflate(inflater, container, false).apply {
//            progressBarBinding = ProgressBarBinding.bind(root)
            }


        binding.btnAddGraduationDiploma.setOnClickListener(View.OnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_nav_education_details_to_engineerAddGraduationDiplomaFragment);
        })

        binding.btnAddMasterGraduation.setOnClickListener(View.OnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_nav_education_details_to_engineerAddPostGraduationFragment);
        })

        binding.btnAddTwelth.setOnClickListener(View.OnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_nav_education_details_to_engineerAddTwelthDetailFragment);
        })

        binding.btnAddTenth.setOnClickListener(View.OnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_nav_education_details_to_engineerAddTenthDetailFragment);
        })


        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}