package com.xiaowugui.e_scimoc.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.xiaowugui.e_scimoc.databinding.DetailFragmentBinding
import com.xiaowugui.e_scimoc.viewModel.ComicsDetailViewModel
import com.xiaowugui.e_scimoc.viewModel.ComicsDetailViewModelFactory

class DetailFragment: Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = DetailFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val comics = DetailFragmentArgs.fromBundle(requireArguments()).selectedComics
        val viewModelFactory = ComicsDetailViewModelFactory(comics, application)
        binding.comicsDetailViewModel = ViewModelProvider(this, viewModelFactory).get(ComicsDetailViewModel::class.java)
        return binding.root
    }
}