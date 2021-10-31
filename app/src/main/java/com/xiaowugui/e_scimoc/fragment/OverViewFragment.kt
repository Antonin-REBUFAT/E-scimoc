package com.xiaowugui.e_scimoc.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.xiaowugui.e_scimoc.viewModel.ComicsOverviewViewModel
import com.xiaowugui.e_scimoc.databinding.OverviewFragmentBinding

class OverViewFragment : Fragment(){

    private val comicsViewModel: ComicsOverviewViewModel by lazy {
        ViewModelProvider(this).get(ComicsOverviewViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        // binding data
        val binding = OverviewFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.comicsViewModel = comicsViewModel

        return binding.root
    }
}