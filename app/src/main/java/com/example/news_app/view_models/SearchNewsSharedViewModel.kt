package com.example.news_app.view_models

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news_app.interfaces.OnFetchDataListener
import com.example.news_app.models.FilterOptions
import com.example.news_app.models.NewsApiResponse
import com.example.news_app.models.NewsHeadlines
import com.example.news_app.models.Sources
import com.example.news_app.repos.NewsApiResponseManager
import retrofit2.Response
import kotlin.collections.ArrayList

class SearchNewsSharedViewModel(application: Application) : AndroidViewModel(application) {

    private val newsManager: NewsApiResponseManager = NewsApiResponseManager()
    var options: FilterOptions = FilterOptions()
    var totalResults: MutableLiveData<Int> = MutableLiveData()
    var newsList = MutableLiveData<ArrayList<NewsHeadlines>>()

    fun getNewsByOptions(options: FilterOptions) {
        val listener: OnFetchDataListener = object:
            OnFetchDataListener {
            override fun onFetchData(newsHeadlines: ArrayList<NewsHeadlines>, message: String) {
                Log.d("Message" , message)
            }
            override fun onFetchResponse(response: Response<NewsApiResponse?>, message: String) {
                newsList.value = response.body()!!.articles
                totalResults.value = response.body()!!.totalResults
            }
            override fun onError(message: String) {
                Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show();
            }
            override fun onFetchDataSources(sources: ArrayList<Sources>, message: String) { Log.d("Message", message) }
        }
        newsManager.getEverythingByOptions(options, listener)
    }
}