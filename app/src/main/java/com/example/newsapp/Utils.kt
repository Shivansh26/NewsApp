package com.example.newsapp

object Utils  {
    val API_KEY = "2f032b5e21854cad90c9953a6e3e3766"
    fun getNewsArticles(source: String): String {
        return StringBuilder("https://newsapi.org/v2/top-headlines?sources=")
            .append(source)
            .append("&apiKey=")
            .append(API_KEY)
            .toString()
    }
}