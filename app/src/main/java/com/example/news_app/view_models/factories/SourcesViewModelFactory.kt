package com.example.news_app.view_models.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news_app.view_models.SourcesViewModel
import com.example.news_app.view_models.TopNewsViewModel

class SourcesViewModelFactory(private val application: Application): ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(
                SourcesViewModel::class.java)) {
            return SourcesViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}