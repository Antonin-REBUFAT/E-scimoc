package com.xiaowugui.e_scimoc.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xiaowugui.e_scimoc.model.Comics

class ComicsDetailViewModelFactory(
    private val comics: Comics,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComicsDetailViewModel::class.java)) {
            return ComicsDetailViewModel(comics, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}