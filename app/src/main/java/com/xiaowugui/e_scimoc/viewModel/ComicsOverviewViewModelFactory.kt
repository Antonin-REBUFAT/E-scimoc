package com.xiaowugui.e_scimoc.viewModel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.xiaowugui.e_scimoc.service.MarvelApiService

class ComicsOverviewViewModelFactory (
    owner: SavedStateRegistryOwner,
    private val service: MarvelApiService
) : AbstractSavedStateViewModelFactory(owner, null){
    override fun <T : ViewModel?> create(key: String,
                                         modelClass: Class<T>,
                                         handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(ComicsOverviewViewModel::class.java)) {
            return ComicsOverviewViewModel(service) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}