package com.example.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class News (

	@SerializedName("status") val status : String,
	@SerializedName("sources") val sources : ArrayList<Sources>
)