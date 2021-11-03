package com.xiaowugui.e_scimoc

import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.xiaowugui.e_scimoc.service.MarvelApiService
import com.xiaowugui.e_scimoc.viewModel.ComicsOverviewViewModelFactory

object Injection {
    private fun provideMarvelApiService(): MarvelApiService {
        return MarvelApiService.create()
    }

    fun provideViewModelFactory(owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
        return ComicsOverviewViewModelFactory(owner, provideMarvelApiService())
    }
}