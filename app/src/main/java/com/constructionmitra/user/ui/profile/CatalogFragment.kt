package com.constructionmitra.user.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.constructionmitra.user.R
import com.constructionmitra.user.adapters.CatalogAdapter
import com.constructionmitra.user.adapters.CatalogPreviewAdapter
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.WorkHistory
import com.constructionmitra.user.databinding.FragmentCatalogBinding
import com.constructionmitra.user.databinding.FragmentProfileBinding
import com.constructionmitra.user.databinding.ItemProfileCard3Binding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.view.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CatalogFragment : Fragment() {

    private lateinit var binding: FragmentCatalogBinding
    private lateinit var progressBarBinding: ProgressBarBinding

    @Inject
    lateinit var appPreferences: AppPreferences

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCatalogBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // get work history
        lifecycleScope.launchWhenResumed {
            showProgress(true)
            viewModel.workHistory(appPreferences.getUserId()!!)
        }

        viewModel.workHistory.observe(viewLifecycleOwner) {
            showProgress(false)
            it?.let {
                var newList: MutableList<WorkHistory> = it as MutableList<WorkHistory>
                newList = newList.asSequence().plus( it[0]).plus(it[0]).plus(it[0]).plus(it[0]).plus(it[0]).plus(it[0])
                    .toList() as MutableList<WorkHistory>
                binding.rvCatalog.adapter  = CatalogAdapter(
                    newList,
                    onItemClick = {

                    }
                )
                binding.rvCatalog.addItemDecoration(GridSpacingItemDecoration(2, 30, true, 0))
            }
        }
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            CatalogFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}