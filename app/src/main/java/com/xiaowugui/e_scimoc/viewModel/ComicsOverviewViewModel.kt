package com.xiaowugui.e_scimoc.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xiaowugui.e_scimoc.model.ApiStatus
import com.xiaowugui.e_scimoc.model.Comics
import com.xiaowugui.e_scimoc.service.MarvelApi
import kotlinx.coroutines.launch

class ComicsOverviewViewModel : ViewModel() {

    /**
     * status of the retrieving process
     */
    private val _status = MutableLiveData<ApiStatus>()

    val status: LiveData<ApiStatus>
        get() = _status

    /**
     * list of comics retrieved
     */
    private val _listComics= MutableLiveData<List<Comics>>()

    val listComics: LiveData<List<Comics>>
        get() = _listComics

    /**
     * Call getMarvelComics() on init to display status immediately.
     */
    init {
        getMarvelComics()
    }

    /**
     * Sets the value of the status LiveData to the Marvel API status.
     */
    private fun getMarvelComics() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val comicsResponse = MarvelApi.retrofitService.getComics()
                val listComics = comicsResponse.data.results

                _status.value = ApiStatus.DONE
                if (listComics.isNotEmpty()) {
                    _listComics.value = listComics
                }
            } catch (e: Exception){
                _status.value = ApiStatus.ERROR
                _listComics.value = ArrayList()
            }
        }
    }
}