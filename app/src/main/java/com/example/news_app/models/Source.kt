package com.example.news_app.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class Source(var id: String, var name: String) : Parcelable