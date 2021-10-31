package com.xiaowugui.e_scimoc.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xiaowugui.e_scimoc.model.ComicsResponse
import com.xiaowugui.e_scimoc.service.MarvelApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComicsOverviewViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    /**
     * Call getMarvelComics() on init so we can display status immediately.
     */
    init {
        getMarvelComics()
    }

    /**
     * Sets the value of the status LiveData to the Marvel API status.
     */
    private fun getMarvelComics() {
        viewModelScope.launch {
            try {
                val comicsResponse = MarvelApi.retrofitService.getComics()
                _response.value = comicsResponse.data.results[0].title
            } catch (e: Exception){
                _response.value = "Failure : ${e.message}"
            }
        }
    }
}