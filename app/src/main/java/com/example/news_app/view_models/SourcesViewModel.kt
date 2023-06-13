package com.example.news_app.view_models

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.example.news_app.adapters.SourcesAdapter
import com.example.news_app.interfaces.OnFetchDataListener
import com.example.news_app.models.NewsApiResponse
import com.example.news_app.models.NewsHeadlines
import com.example.news_app.models.Sources
import com.example.news_app.models.constants.PreferencesKeys
import com.example.news_app.repos.NewsApiResponseManager
import retrofit2.Response

class SourcesViewModel(application: Application) : AndroidViewModel(application) {

    private val newsManager: NewsApiResponseManager = NewsApiResponseManager()
    private val preferences = PreferenceManager.getDefaultSharedPreferences(application)
    var sourcesCountry: String = preferences.getString(PreferencesKeys.SOURCES_COUNTRY, "us")!!
    var sourcesCategory: String = preferences.getString(PreferencesKeys.SOURCES_CATEGORY, "")!!
    var sourcesList = MutableLiveData<ArrayList<Sources>>()
    private val sourcesCountryChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { prefs, key -> // listener implementation
            when (key) {
                PreferencesKeys.SOURCES_COUNTRY -> {
                    sourcesCountry = prefs?.getString(key, "us")!!
                    getSources(sourcesCountry, sourcesCategory)
                }
            }
        }
    private val sourcesCategoryChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { prefs, key -> // listener implementation
            when (key) {
                PreferencesKeys.SOURCES_CATEGORY -> {
                    sourcesCategory = prefs?.getString(key, "us")!!
                    getSources(sourcesCountry, sourcesCategory)
                }
            }
        }

    init {
        preferences.registerOnSharedPreferenceChangeListener(sourcesCountryChangeListener)
        preferences.registerOnSharedPreferenceChangeListener(sourcesCategoryChangeListener)
        getSources(sourcesCountry, sourcesCategory)
    }

    fun getSources(country: String, category: String) {
        val listener: OnFetchDataListener = object:
            OnFetchDataListener {

            override fun onFetchData(newsHeadlines: ArrayList<NewsHeadlines>, message: String) {  Log.d("Message", message) }
            override fun onFetchResponse(response: Response<NewsApiResponse?>, message: String) { Log.d("Message", message) }
            override fun onError(message: String) { Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show(); }
            override fun onFetchDataSources(sources: ArrayList<Sources>, message: String) { sourcesList.value = sources }
        }
        newsManager.getSources(country, category, null, listener)
    }

}