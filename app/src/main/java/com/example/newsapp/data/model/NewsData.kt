package com.example.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class NewsData(
    @SerializedName("status")var status: String,
    @SerializedName("totalResults")var totalResults: Int,
    @SerializedName("articles")var articles: ArrayList<Article>
)
