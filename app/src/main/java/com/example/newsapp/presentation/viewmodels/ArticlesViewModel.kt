package com.example.newsapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.Utils
import com.example.newsapp.data.api.ApiClient
import com.example.newsapp.data.api.ApiInterface
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.NewsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticlesViewModel : ViewModel() {
    val mainNewsLiveData = MutableLiveData<NewsData>()
    val articleListLiveData = MutableLiveData<ArrayList<Article>>()
    fun filterArticlesData(query: String){
        val newsData = mainNewsLiveData.value ?: return
        if(newsData.articles.isEmpty()) return
        if(query.isBlank()){
            articleListLiveData.postValue(newsData.articles)
        }  else {
            val articlesList = ArrayList<Article>()
            newsData.articles.forEach { article ->
                if(article.title.contains(query, true)){
                    articlesList.add(article)
                }
            }
            articleListLiveData.postValue(articlesList)
        }
    }

    fun fetchArticlesFromSources(sources: String){
        val retroInstance = ApiClient.getClient().create(ApiInterface::class.java)
        retroInstance.getNewsFromSources(Utils.getNewsArticles(sources)).enqueue(object : Callback<NewsData>{
            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                mainNewsLiveData.value = response.body()
                filterArticlesData("")
                Log.d("Success", "Articles Call successful")
            }

            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                Log.e("Articles Error", t.message.toString())
            }

        })
    }
}