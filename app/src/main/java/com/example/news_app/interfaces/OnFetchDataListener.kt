package com.example.news_app.interfaces

import android.media.tv.AdResponse
import com.example.news_app.models.NewsApiResponse
import com.example.news_app.models.NewsApiSourcesResponse
import com.example.news_app.models.NewsHeadlines
import com.example.news_app.models.Sources
import retrofit2.Response

interface OnFetchDataListener {
    fun onFetchData(newsHeadlines: ArrayList<NewsHeadlines>, message: String)
    fun onFetchResponse(response: Response<NewsApiResponse?>, message: String)
    fun onError(message: String)
    fun onFetchDataSources(sources: ArrayList<Sources>, message: String)
}