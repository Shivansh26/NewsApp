package com.example.newsapp

import android.view.View

interface ItemClickListener {
    fun onClick(view: View, position: Int)
}