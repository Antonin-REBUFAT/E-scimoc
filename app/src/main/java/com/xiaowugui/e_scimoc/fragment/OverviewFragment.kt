package com.xiaowugui.e_scimoc.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.coroutines.flow.collect
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.xiaowugui.e_scimoc.Injection
import com.xiaowugui.e_scimoc.adapter.ImageGridAdapter
import com.xiaowugui.e_scimoc.viewModel.ComicsOverviewViewModel
import com.xiaowugui.e_scimoc.databinding.OverviewFragmentBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.xiaowugui.e_scimoc.adapter.ComicsLoadStateAdapter


class OverviewFragment : Fragment(){

    private lateinit var comicsViewModel: ComicsOverviewViewModel
    private lateinit var binding :OverviewFragmentBinding
    private val adapter = ImageGridAdapter(ImageGridAdapter.OnClickListener {
        comicsViewModel.displayComicsDetails(it)
    })

    private var searchJob: Job? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        comicsViewModel = ViewModelProvider(this, Injection.provideViewModelFactory(owner = this)).get(ComicsOverviewViewModel::class.java)
        binding = OverviewFragmentBinding.inflate(inflater,container, false)

        binding.lifecycleOwner = this

        initLayoutManager()

        // init navigation to detail
        comicsViewModel.navigateToSelectedComics.observe(viewLifecycleOwner, Observer {
            if(null != it){
                this.findNavController().navigate(OverviewFragmentDirections.actionShowComicsDetail(it))
                comicsViewModel.displayComicsDetailsComplete()
            }
        })
        // set research value
        val startWith = DEFAULT_STARTS_WITH

        initAdapter()
        search(startWith)

        initSearch(startWith)
        return binding.root
    }

    /**
     * init the layout manager and change the span size for the loading footer
     */
    private fun initLayoutManager() {
        val gridLayoutManager = GridLayoutManager(activity, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = adapter.getItemViewType(position)
                return if(viewType == ImageGridAdapter.IMAGE_VIEW_TYPE) 1 else 2
            }
        }
        binding.comicsGrid.layoutManager = gridLayoutManager
    }

    /**
     * bind the adapter to the recyclerView and the retry button
     */
    private fun initAdapter(){
        binding.comicsGrid.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ComicsLoadStateAdapter { adapter.retry() },
            footer = ComicsLoadStateAdapter { adapter.retry() }
        )

        binding.retryButton.setOnClickListener { adapter.retry() }

        adapter.addLoadStateListener { loadState ->
            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            showEmptyList(isListEmpty)

            // set the visibility of the recyclerView, loader and error
            binding.comicsGrid.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    context,
                    "Erreur de chargement : ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    /**
     * function to hide the keyboard
     */
    private fun hideKeyBoard(){
        val imm: InputMethodManager? =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    /**
     * init the binding of the research field and the search
     */
    private fun initSearch(startWith: String?) {
        binding.searchComics.setText(startWith)

        binding.searchComics.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateComicsListFromInput()
                hideKeyBoard()
                true
            } else {
                false
            }
        }
        binding.searchComics.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateComicsListFromInput()
                hideKeyBoard()
                true
            } else {
                false
            }
        }
    }

    /**
     * update the search with the content of the search field
     */
    private fun updateComicsListFromInput() {
        binding.searchComics.text.trim().let {
            if (it.isNotEmpty()) {
                search(it.toString())
            }else{
                search(null)
            }
        }
    }

    /**
     * switch the visibility of the empty list view
     */
    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.emptyList.visibility = View.VISIBLE
            binding.comicsGrid.visibility = View.GONE
        } else {
            binding.emptyList.visibility = View.GONE
            binding.comicsGrid.visibility = View.VISIBLE
        }
    }


    /**
     * bind the search data from the api to the adapter
     */
    private fun search(startWith: String?) {
        searchJob?.cancel()
        searchJob = viewLifecycleOwner.lifecycleScope.launch {
            comicsViewModel.searchComics(startWith).collect {
                adapter.submitData(it)
            }
        }
    }

    companion object {
        /**
         * Default search field value
         */
        private val DEFAULT_STARTS_WITH = null
    }
}