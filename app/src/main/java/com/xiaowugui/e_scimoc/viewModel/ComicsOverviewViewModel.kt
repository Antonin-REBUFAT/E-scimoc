package com.xiaowugui.e_scimoc.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.xiaowugui.e_scimoc.model.Comics
import com.xiaowugui.e_scimoc.service.ApiConstant
import com.xiaowugui.e_scimoc.service.ComicsPagingSource
import com.xiaowugui.e_scimoc.service.MarvelApiService
import kotlinx.coroutines.flow.Flow

class ComicsOverviewViewModel(
    private val service: MarvelApiService
) : ViewModel() {

    // searching process

    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<Comics>>? = null

    fun searchComics(startWith: String?): Flow<PagingData<Comics>> {
        val lastResult = currentSearchResult
        if (startWith == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = startWith
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstant.limit,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ComicsPagingSource(service, startWith) }
        ).flow
            .cachedIn(viewModelScope)
    }

    // navigation to detail view

    private val _navigateToSelectedComics = MutableLiveData<Comics>()

    val navigateToSelectedComics: LiveData<Comics>
        get() = _navigateToSelectedComics

    fun displayComicsDetails(comics: Comics) {
        _navigateToSelectedComics.value = comics
    }

    fun displayComicsDetailsComplete() {
        _navigateToSelectedComics.value = null
    }
}