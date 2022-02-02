package com.constructionmitra.user.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.constructionmitra.user.R
import com.constructionmitra.user.adapters.LocationAdapter
import com.constructionmitra.user.api.ProfileRequests
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.WorkExperience
import com.constructionmitra.user.data.WorkPreference
import com.constructionmitra.user.databinding.*
import com.constructionmitra.user.utilities.StringUtils
import com.constructionmitra.user.utilities.ext.app
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class WorkPreferenceFragment : Fragment() {

    private lateinit var binding: FragmentWorkPreferenceBinding
    private lateinit var progressBarBinding: ProgressBarBinding
    private var isUpdated: Boolean = false

    private val appDataConfig by lazy { app().appConfigData }

    @Inject
    lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var profileRequests: ProfileRequests

    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if(isUpdated)
                requireActivity().setResult(AppCompatActivity.RESULT_OK)
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWorkPreferenceBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
            tvSave.setOnClickListener {
                with(rvWorkPreferences.adapter as WorkPreferenceAdapter){
                    selectedWork()?.let { workPreference ->
                        showProgress(true)
                        viewModel.updateProfileData(profileRequests.updateWorkPreference(
                            userId =  appPreferences.getUserId(),
                            token = appPreferences.getToken() ?: "",
                            workPreference = workPreference
                        ))
                    }
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)
        appDataConfig?.let {
            binding.rvWorkPreferences.adapter = WorkPreferenceAdapter(it.workPreferences){ workPreference ->

            }.apply {
                appPreferences.workPreference()?.let { workPreference ->
                    setSelection(workPreference)
                }
            }
        }
        registerObserver()
    }

    private fun registerObserver(){
        viewModel.profileDataUpdated.observe(viewLifecycleOwner) {
            showProgress(false)
            binding.root.showToast(getString(R.string.profile_updated_hn))
            it.takeIf { it.workPreferences.isNotEmpty() }?.let {  profileData ->
                log("Work Preference updated: ${profileData.workPreferences[0]}")
                appPreferences.updateWorkPreference(profileData.workPreferences[0])
            }
        }
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            WorkPreferenceFragment().apply {
                arguments = Bundle().apply {

                }
            }

        fun log(msg: String){
            Timber.d("cmitra: WorkPreferenceFragment: $msg")
        }
    }
}

class WorkPreferenceAdapter(
    val list: List<WorkPreference>,
    private val onItemClick: (workPreference: WorkPreference) -> Unit
): RecyclerView.Adapter<WorkPreferenceAdapter.WorkPreferenceViewHolder>() {
    private var _currentSelection = -1
    private val currentSelection
        get() = _currentSelection

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkPreferenceViewHolder {
        val binding: ItemWorkPreferenceBinding = ItemWorkPreferenceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WorkPreferenceViewHolder(binding)
    }

    fun selectedWork(): WorkPreference? = if(currentSelection == -1 ) null else list[currentSelection]

    fun setSelection(workPreference: WorkPreference){
        for (i in list.indices){
            if(list[i].workPreferenceId == workPreference.workPreferenceId)
                _currentSelection = i
        }
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: WorkPreferenceViewHolder, position: Int) {
        holder.bindData(onItemClick, list[position])
    }

    inner class WorkPreferenceViewHolder(val binding: ItemWorkPreferenceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            onItemClick: (workPreference: WorkPreference) -> Unit,
            workPreference: WorkPreference,
        ) {
            binding.tvName.text = workPreference.toString()
            binding.ivTick.visibility =
                if( adapterPosition == _currentSelection) View.VISIBLE else View.GONE

            binding.root.setOnClickListener{
                _currentSelection = adapterPosition
                notifyDataSetChanged()
            }
        }
    }
}