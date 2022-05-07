package com.example.newsapp.presentation.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.presentation.adapters.ArticlesAdapter
import com.example.newsapp.presentation.viewmodels.ArticlesViewModel
import kotlinx.android.synthetic.main.fragment_articles.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ArticlesFragment : Fragment() {
    private lateinit var articlesViewModel: ArticlesViewModel
    private lateinit var articlesAdapter: ArticlesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle: Bundle? = this.arguments
        setupRecyclerView()
        val sourceString: String = bundle?.getString("sources").toString()
        articlesViewModel.fetchArticlesFromSources(sourceString)
        observeAndUpdateData()
    }

    private fun setupRecyclerView(){
        articlesAdapter = ArticlesAdapter(requireActivity(), arrayListOf())
        articlesRv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = articlesAdapter
            setHasFixedSize(true)
        }
    }

    private fun observeAndUpdateData(){
        articlesViewModel.articleListLiveData.observe(viewLifecycleOwner) { news ->
            news.let {
                articlesAdapter.updateNewsArticles(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                lifecycleScope.launch {
                    searchArticles(query ?: "")
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                lifecycleScope.launch {
                    searchArticles(query ?: "")
                }
                return false
            }
        })
    }

    private suspend fun searchArticles(query: String?) {
        delay(300)
        articlesViewModel.filterArticlesData(query ?: "")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_search) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        articlesViewModel = ViewModelProvider(this).get(ArticlesViewModel::class.java)
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_articles, container, false)
    }
}