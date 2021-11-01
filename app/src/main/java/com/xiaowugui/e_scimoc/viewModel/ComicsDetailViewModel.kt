package com.xiaowugui.e_scimoc.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.xiaowugui.e_scimoc.R
import com.xiaowugui.e_scimoc.model.Comics
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ComicsDetailViewModel(comics: Comics, application: Application): AndroidViewModel(application) {

    private val _selectedComics = MutableLiveData<Comics>()
    val selectedComics: LiveData<Comics>
        get() = _selectedComics

    init {
        _selectedComics.value = comics
    }

    val displaySaleDate = Transformations.map(selectedComics) {
        if(it.onSaleDate.isEmpty()){
            application.applicationContext.getString(R.string.unknown)
        }else{
            val date = LocalDate.parse(it.onSaleDate.split("T")[0])
            date.format(DateTimeFormatter.ofPattern("dd/MM/uuuu"))
        }
    }

    val displayPageCount = Transformations.map(selectedComics) {
        if (it.pageCount == 0){
            application.applicationContext.getString(R.string.unknown)
        } else {
            it.pageCount.toString()
        }
    }

    val displayFormat = Transformations.map(selectedComics) {
        if(it.format.isEmpty()){
            application.applicationContext.getString(R.string.unknown)
        } else {
            it.format
        }
    }

    val displayISBN = Transformations.map(selectedComics) {
        if(it.isbn.isEmpty()){
            application.applicationContext.getString(R.string.unknown)
        } else {
            it.isbn
        }
    }

    val displayPrice = Transformations.map(selectedComics) {
        if(it.price == 0.0){
            application.applicationContext.getString(R.string.unknown)
        } else {
            application.applicationContext.getString(R.string.price_formatter, it.price)
        }
    }

    val displayDescription = Transformations.map(selectedComics) {
        if(it.description.isNullOrEmpty()){
            application.applicationContext.getString(R.string.unknown)
        } else {
            it.description
        }
    }
    val displaySeries = Transformations.map(selectedComics) {
        if(it.series.isEmpty()){
            application.applicationContext.getString(R.string.unknown)
        } else {
            it.series
        }
    }
}