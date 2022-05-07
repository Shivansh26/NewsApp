package com.example.newsapp.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.presentation.ui.ArticlesFragment
import com.example.newsapp.ItemClickListener
import com.example.newsapp.R
import com.example.newsapp.data.model.Sources
import kotlinx.android.synthetic.main.source_item.view.*

class SourceAdapter(val context: Context, private val sources: ArrayList<Sources>) :
    RecyclerView.Adapter<SourceAdapter.SourceViewHolder>() {
    class SourceViewHolder(val card: View) : RecyclerView.ViewHolder(card), View.OnClickListener {
        private lateinit var itemClickListener: ItemClickListener

        init {
            card.setOnClickListener(this)
        }

        fun setItemClickListener(itemClickListener: ItemClickListener) {
            this.itemClickListener = itemClickListener
        }

        override fun onClick(card: View) {
            itemClickListener.onClick(card, adapterPosition)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateNewsSources(sourcesList: ArrayList<Sources>) {
        sources.clear()
        sources.addAll(sourcesList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.source_item, parent, false)
        return SourceViewHolder(view)
    }

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        holder.card.news_source.text = sources[position].name
        holder.card.source_desc.text = sources[position].description
        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val activity: AppCompatActivity = context as AppCompatActivity
                val articlesFragment = ArticlesFragment()
                val bundle = Bundle()
                bundle.putString("sources", sources[position].id)
                articlesFragment.arguments = bundle
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.container, articlesFragment, "ArticlesFragment")
                    .addToBackStack(null).commit()
            }
        })
    }

    override fun getItemCount(): Int = sources.size

}