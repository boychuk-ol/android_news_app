package com.example.news_app.view_models

import android.app.Application
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.example.news_app.activities.MainActivity
import com.example.news_app.interfaces.OnFetchDataListener
import com.example.news_app.models.NewsApiResponse
import com.example.news_app.models.NewsHeadlines
import com.example.news_app.models.Sources
import com.example.news_app.models.constants.PreferencesKeys
import com.example.news_app.repos.NewsApiResponseManager
import retrofit2.Response
import kotlin.collections.ArrayList

class TopNewsViewModel(application: Application) : AndroidViewModel(application) {

    private val newsManager: NewsApiResponseManager = NewsApiResponseManager()
    private val preferences = PreferenceManager.getDefaultSharedPreferences(application)
    var newsList = MutableLiveData<ArrayList<NewsHeadlines>>()
    var country: String = preferences.getString(PreferencesKeys.RECENT_NEWS_COUNTRY, "us")!!
    var category: String = "general"
    var query: String? = null
    var tabPosition: Int = 0
    private val newsCountryPreferenceChangeListener =
        OnSharedPreferenceChangeListener { prefs, key -> // listener implementation
            when (key) {
                PreferencesKeys.RECENT_NEWS_COUNTRY -> {
                    country = prefs?.getString(key, "us")!!
                    getNews(country, category, query)
                }
            }
        }

    init {
        preferences.registerOnSharedPreferenceChangeListener(newsCountryPreferenceChangeListener)
        country = preferences.getString(PreferencesKeys.RECENT_NEWS_COUNTRY, "us")!!
        getNews(country, category, query)
    }

    fun getNews(country: String, category: String, query: String?) {

        val listener: OnFetchDataListener = object: OnFetchDataListener {
            override fun onFetchData(newsHeadlines: ArrayList<NewsHeadlines>, message: String) { newsList.value = newsHeadlines }
            override fun onFetchResponse(response: Response<NewsApiResponse?>, message: String) { Log.d("Message", message) }
            override fun onError(message: String) { Log.d("Error", message) }
            override fun onFetchDataSources(sources: ArrayList<Sources>, message: String) { Log.d("Message", message) }
        }
        newsManager.getHeadlines(country, category, null, query, 30, listener)
    }

}