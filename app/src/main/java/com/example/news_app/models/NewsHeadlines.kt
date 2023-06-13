package com.example.news_app.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class NewsHeadlines(val source: Source?,
                    val author: String?,
                    val title: String?,
                    val description: String?,
                    val url: String?,
                    val urlToImage: String?,
                    val publishedAt: String?,
                    val content: String?) : Parcelable