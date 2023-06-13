package com.example.news_app.view_models.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news_app.view_models.SearchNewsSharedViewModel

class SearchNewsSharedViewModelFactory(private val application: Application): ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(
                SearchNewsSharedViewModel::class.java)) {
            return SearchNewsSharedViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}