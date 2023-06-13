package com.example.news_app.models

import android.os.Parcelable
import com.example.news_app.models.constants.NewsFilterConstants
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.util.*


@Parcelize
data class FilterOptions(var language: String? = NewsFilterConstants.DEFAULT_FILTER_LANGUAGE,
                         var sortBy: String? = NewsFilterConstants.DEFAULT_FILTER_SORT_BY,
                         var source: String? = NewsFilterConstants.DEFAULT_FILTER_SOURCE,
                         var query: String? = NewsFilterConstants.DEFAULT_FILTER_QUERY,
                         var dateFrom: LocalDate? = LocalDate.now(),
                         var dateTo: LocalDate? = LocalDate.now()) : Parcelable {
    fun setDefault() {
        this.language = NewsFilterConstants.DEFAULT_FILTER_LANGUAGE
        this.sortBy = NewsFilterConstants.DEFAULT_FILTER_SORT_BY
        this.source = NewsFilterConstants.DEFAULT_FILTER_SOURCE
        this.query = NewsFilterConstants.DEFAULT_FILTER_QUERY
        this.dateFrom = LocalDate.now()
        this.dateTo = LocalDate.now()
    }
}