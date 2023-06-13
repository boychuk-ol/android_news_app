package com.example.news_app.models

class Sources (id: String,
               name: String,
               val description: String,
               val url: String,
               val category: String,
               val language: String,
               val country: String): Source(id, name)