package com.example.news_app.interfaces

import com.example.news_app.models.NewsApiResponse
import com.example.news_app.models.NewsApiSourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

interface RetrofitAPI {

    @GET("top-headlines")
    fun callHeadlines(@Query("country") country: String?,
                      @Query("category") category: String?,
                      @Query("sources") sources: String?,
                      @Query("q") query: String?,
                      @Query("pageSize") pageSize: Int?,
                      @Query("apiKey") apiKey: String): Call<NewsApiResponse>

    @GET("everything")
    fun callEverything(@Query("language") language: String?,
                       @Query("searchIn") searchIn: String?,
                       @Query("from") from: String?,
                       @Query("to") to: String?,
                       @Query("q") query: String?,
                       @Query("sortBy") sortBy: String?,
                       @Query("pageSize") pageSize: Int?,
                       @Query("sources") sources: String?,
                       @Query("apiKey") apiKey: String): Call<NewsApiResponse>

    @GET("top-headlines/sources")
    fun  callSources(@Query("country") country: String?,
                     @Query("category") category: String?,
                     @Query("language") language: String?,
                     @Query("apiKey") apiKey: String): Call<NewsApiSourcesResponse>
}