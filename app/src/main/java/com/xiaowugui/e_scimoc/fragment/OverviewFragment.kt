package com.xiaowugui.e_scimoc.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.xiaowugui.e_scimoc.adapter.ImageGridAdapter
import com.xiaowugui.e_scimoc.viewModel.ComicsOverviewViewModel
import com.xiaowugui.e_scimoc.databinding.OverviewFragmentBinding

class OverviewFragment : Fragment(){

    private val comicsViewModel: ComicsOverviewViewModel by lazy {
        ViewModelProvider(this).get(ComicsOverviewViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        // binding data
        val binding = OverviewFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.comicsViewModel = comicsViewModel
        binding.comicsGrid.adapter = ImageGridAdapter(ImageGridAdapter.OnClickListener {
            comicsViewModel.displayComicsDetails(it)
        })
        comicsViewModel.navigateToSelectedComics.observe(viewLifecycleOwner, Observer {
            if(null != it){
                this.findNavController().navigate(OverviewFragmentDirections.actioShowComicsDetail(it))
                comicsViewModel.displayComicsDetailsComplete()
            }
        })

        return binding.root
    }
}