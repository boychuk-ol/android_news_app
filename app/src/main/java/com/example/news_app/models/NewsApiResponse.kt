package com.example.news_app.models

class NewsApiResponse(val status: String,
                      val totalResults: Int,
                      val articles: ArrayList<NewsHeadlines>)