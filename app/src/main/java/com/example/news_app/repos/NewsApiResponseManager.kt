package com.example.news_app.repos

import android.util.Log
import com.example.news_app.interfaces.OnFetchDataListener
import com.example.news_app.interfaces.RetrofitAPI
import com.example.news_app.models.FilterOptions
import com.example.news_app.models.NewsApiResponse
import com.example.news_app.models.NewsApiSourcesResponse
import com.example.news_app.models.Sources
import org.json.JSONObject
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

const val BASE_URL = "https://newsapi.org/v2/"
const val API_KEY = "da30cbb609124018915d9fa98c0bd514"
//const val API_KEY = "bedd92191044463ba2a1d9fd421b4c04"

class NewsApiResponseManager {

    val retrofit: Retrofit =  Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val retrofitAPI: RetrofitAPI = retrofit.create(RetrofitAPI::class.java)


    fun getHeadlines(country: String?, category: String? = null, sources: String? = null, query: String? = null, pageSize: Int? = 50, listener: OnFetchDataListener) {

        val call: Call<NewsApiResponse> = retrofitAPI.callHeadlines(country, category, sources, query, pageSize, API_KEY)

        call.enqueue(object : Callback<NewsApiResponse?> {
            override fun onResponse(call: Call<NewsApiResponse?>, response: Response<NewsApiResponse?>) {
                if (response.isSuccessful) {
                    listener.onFetchData(response.body()!!.articles, response.message())
                } else {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    listener.onError(jObjError.getString("message"))
                }
            }

            override fun onFailure(call: Call<NewsApiResponse?>, t: Throwable) {
                listener.onError(t.message.toString())
            }
        })

    }

    fun getEverythingBySource (sources: String, listener: OnFetchDataListener) {
        val call: Call<NewsApiResponse> = retrofitAPI.callEverything(null, null, null, null, null, null, 50, sources, API_KEY)

        call.enqueue(object : Callback<NewsApiResponse?> {
            override fun onResponse(call: Call<NewsApiResponse?>, response: Response<NewsApiResponse?>) {
                if (response.isSuccessful) {
                    listener.onFetchData(response.body()!!.articles, response.message())
                }
                else {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    listener.onError(jObjError.getString("message"))
                }
            }

            override fun onFailure(call: Call<NewsApiResponse?>, t: Throwable) {
                listener.onError(t.message.toString())
            }

        })
    }

    fun getEverythingByOptions (options: FilterOptions, listener: OnFetchDataListener) {
        val call: Call<NewsApiResponse> = retrofitAPI.callEverything(options.language, null, options.dateFrom.toString(), options.dateTo.toString(), options.query, options.sortBy, 50, options.source, API_KEY)

        call.enqueue(object : Callback<NewsApiResponse?> {
            override fun onResponse(call: Call<NewsApiResponse?>, response: Response<NewsApiResponse?>) {
                if (response.isSuccessful) {
                    listener.onFetchResponse(response, response.message())
                    //listener.onFetchData(response.body()!!.articles, response.message())
                }
                else {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    listener.onError(jObjError.getString("message"))
                }
            }

            override fun onFailure(call: Call<NewsApiResponse?>, t: Throwable) {
                listener.onError(t.message.toString())
            }

        })
    }

    fun getEverything(language: String?, searchIn: String?, from: LocalDate?, to: LocalDate?, query: String?, sortBy: String?, pageSize: Int? = 50, sources: String?, listener: OnFetchDataListener) {

        val call: Call<NewsApiResponse> = retrofitAPI.callEverything(language, searchIn, from.toString(), to.toString(), query, sortBy, pageSize, sources, API_KEY)

        call.enqueue(object : Callback<NewsApiResponse?> {
            override fun onResponse(call: Call<NewsApiResponse?>, response: Response<NewsApiResponse?>) {
                if (response.isSuccessful) {
                    listener.onFetchData(response.body()!!.articles, response.message())
                }
                else {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    listener.onError(jObjError.getString("message"))
                }
            }

            override fun onFailure(call: Call<NewsApiResponse?>, t: Throwable) {
                listener.onError(t.message.toString())
            }

        })
    }

    fun getSources(country: String?, category: String?, language: String?, listener: OnFetchDataListener) {
        val call: Call<NewsApiSourcesResponse> = retrofitAPI.callSources(country, category, language, API_KEY)

        call.enqueue(object : Callback<NewsApiSourcesResponse?> {
            override fun onResponse(call: Call<NewsApiSourcesResponse?>, response: Response<NewsApiSourcesResponse?>) {
                if (response.isSuccessful) {
                    listener.onFetchDataSources(response.body()!!.sources, response.message())
                }
                else {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    listener.onError(jObjError.getString("message"))
                }
            }

            override fun onFailure(call: Call<NewsApiSourcesResponse?>, t: Throwable) {
                listener.onError(t.message.toString())
            }

        })
    }

}