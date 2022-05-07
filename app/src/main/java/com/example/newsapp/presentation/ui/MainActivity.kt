package com.example.newsapp.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R

class MainActivity :AppCompatActivity() {
    lateinit var sourceListFragment: SourceListFragment
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showSourceFragment()
    }

    private fun showSourceFragment() {
        sourceListFragment = SourceListFragment()
        supportFragmentManager.beginTransaction().add(R.id.container,sourceListFragment, "SourceListFragment" ).commit()
    }
}