package com.example.newsapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.Utils
import com.example.newsapp.data.api.ApiClient
import com.example.newsapp.data.api.ApiInterface
import com.example.newsapp.data.model.News
import com.example.newsapp.data.model.Sources
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    val news = MutableLiveData<News>()
    val sourcesListLiveData = MutableLiveData<ArrayList<Sources>>()

    fun filterSourcesData(query: String) {
        val data = news.value ?: return
        if (data.sources.isEmpty()) return
        if (query.isBlank()) {
            sourcesListLiveData.postValue(data.sources)
        } else {
            val sourcesList = ArrayList<Sources>()
            data.sources.forEach { source ->
                if (source.name.contains(query, true)) {
                    sourcesList.add(source)
                }
            }
            sourcesListLiveData.postValue(sourcesList)
        }
    }

    fun fetchNewsSources() {

        val retroInstance = ApiClient.getClient().create(ApiInterface::class.java)
        retroInstance.getSources(Utils.API_KEY)
            .enqueue(object : Callback<News> {
                override fun onResponse(call: Call<News>, response: Response<News>) {
                    news.value = response.body()
                    filterSourcesData("")
                    Log.d("Success", "Call successful")
                }

                override fun onFailure(call: Call<News>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }

            })

    }


}



