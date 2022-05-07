package com.example.newsapp.data.api

import com.example.newsapp.data.model.News
import com.example.newsapp.data.model.NewsData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiInterface {

    @GET("v2/top-headlines/sources")
    fun getSources(@Query("apiKey") API_KEY:String) : Call<News>

    @GET
    fun getNewsFromSources(@Url url : String) : Call<NewsData>
}