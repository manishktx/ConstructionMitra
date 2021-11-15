package com.constructionmitra.user.ui.employer.engineer_supervisor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.constructionmitra.user.R
import com.constructionmitra.user.data.JobRoleDetails
import com.constructionmitra.user.databinding.*
import com.constructionmitra.user.ui.contractor.viewmodels.JobPostViewModel
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.card_job_roles.view.*
import kotlinx.android.synthetic.main.view_list_job_roles.view.*

@AndroidEntryPoint
class JobRoleDetailsESFragment : Fragment() {

    private var selectedItem: Int? = null
    private var workersRequiredInDays: String? = null
    private var _binding: FragmentJobRoleDetailsEsBinding? = null
    private lateinit var progressBarBinding: ProgressBarBinding

    private val onItemSelectedListener: AdapterView.OnItemClickListener =
        AdapterView.OnItemClickListener {
                parent, view, position, id -> selectedItem = position
        }

    private val viewModel: JobPostViewModel by lazy {
        ViewModelProvider(requireActivity()).get(JobPostViewModel::class.java)
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJobRoleDetailsEsBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            val items = resources.getStringArray(R.array.work_options)
            val adapter = ArrayAdapter(requireContext(), R.layout.item_drop_down, items)
            (jobRoleInput.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            viewModel.onFragmentSelected(0)
        }
        registerObservers()

    }

    private fun registerObservers() {
    }


    companion object{
        private const val MIN_DAYS = "0 - 15"
        private const val MAX_DAYS = "15 - 30"
    }
}