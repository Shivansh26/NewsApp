package com.example.newsapp.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.ItemClickListener
import com.example.newsapp.R
import com.example.newsapp.data.model.Article
import com.example.newsapp.presentation.ui.NewsWebPageActivity
import kotlinx.android.synthetic.main.articles_item.view.*

class ArticlesAdapter (val context: Context, private val articles: ArrayList<Article>) :
    RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder>() {

    var articlesFiltered = ArrayList<Article>()

    init {
        articlesFiltered = articles
    }

    class ArticlesViewHolder(val articlesCard: View) : RecyclerView.ViewHolder(articlesCard),
        View.OnClickListener {
        private lateinit var itemClickListener: ItemClickListener

        init {
            articlesCard.setOnClickListener(this)
        }

        fun setItemClickListener(itemClickListener: ItemClickListener) {
            this.itemClickListener = itemClickListener
        }

        override fun onClick(articlesCard: View) {
            itemClickListener.onClick(articlesCard, adapterPosition)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateNewsArticles(articlesList: ArrayList<Article>) {
        articles.clear()
        articles.addAll(articlesList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.articles_item, parent, false)
        return ArticlesViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        if (articles[position].title.length > 40) {
            holder.articlesCard.news_title.text =
                articles[position].title.substring(0, 40) + " ... Read More"
        } else {
            holder.articlesCard.news_title.text = articles[position].title
        }
        holder.articlesCard.news_desc.text = articles[position].description
        Glide.with(holder.articlesCard.context).load(articles[position].urlToImage)
            .into(holder.articlesCard.news_img)
        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context, NewsWebPageActivity::class.java)
                intent.putExtra("newsUrl", articles[position].url)
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }

        })
    }

    override fun getItemCount(): Int {
        return articlesFiltered.size
    }
}

//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(query: CharSequence?): FilterResults {
//                val charString = query.toString()
//                //articles.clear()
//                if (query.isNullOrBlank()) {
//                    articlesFiltered = articles
//                }
//
//                else{
//                    val filteredList = ArrayList<Article>()
//                    articles.filter { it.title.contains(charString, true) }
//                        .forEach{filteredList.add(it)}
//                    articlesFiltered = filteredList
//                }
//                return FilterResults().apply { values = articlesFiltered }
//            }
//
//            @Suppress("UNCHECKED_CAST")
//            override fun publishResults(query: CharSequence?, results: FilterResults?) {
//                articlesFiltered = results?.values as ArrayList<Article>
//                updateNewsArticles(articlesFiltered)
//                //notifyDataSetChanged()
//            }
//        }
//    }
//}

//    fun filter(query : String){
//        val temp = ArrayList<Article>()
//        if(!TextUtils.isEmpty(query)){
//            for(d in articles){
//                if(d.title.contains(query, true)){
//                    temp.add(d)
//                }
//            }
//        } else{
//            temp.addAll(articles)
//        }
//
//        updateNewsArticles(temp)
//
//
//
//    }




