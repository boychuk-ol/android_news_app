package com.example.news_app.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news_app.interfaces.OnFetchDataListener
import com.example.news_app.models.*
import com.example.news_app.repos.NewsApiResponseManager
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class SourceNewsViewModel: ViewModel() {
    private val newsManager: NewsApiResponseManager =  NewsApiResponseManager()
    var source: Source? = null
    var newsList = MutableLiveData<ArrayList<NewsHeadlines>>()

    fun getNewsBySource(source: Source) {
        val listener: OnFetchDataListener = object:
            OnFetchDataListener {
            override fun onFetchData(
                newsHeadlines: ArrayList<NewsHeadlines>,
                message: String
            ) {
                newsList.value = newsHeadlines
            }
            override fun onFetchResponse(response: Response<NewsApiResponse?>, message: String) { Log.d("Message" ,message) }
            override fun onError(message: String) { Log.d("Error" ,message) }
            override fun onFetchDataSources(sources: ArrayList<Sources>, message: String) { Log.d("Message", message) }
        }
        newsManager.getEverythingBySource(source.id, listener)
    }
}