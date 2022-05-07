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
import com.example.newsapp.presentation.adapters.SourceAdapter
import com.example.newsapp.presentation.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.fragment_source_list.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SourceListFragment : Fragment() {

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var sourceAdapter: SourceAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        newsViewModel.fetchNewsSources()
        observeAndUpdateData()
    }

    private fun observeAndUpdateData() {
        newsViewModel.sourcesListLiveData.observe(viewLifecycleOwner) { news ->
            news.let {
                sourceAdapter.updateNewsSources(it)
            }
        }
    }

    private fun setUpRecyclerView() {
        sourceAdapter = SourceAdapter(requireActivity(), arrayListOf())
        sourcesRv.apply {
            adapter = sourceAdapter
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
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
                    searchSources(query ?: "")
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                lifecycleScope.launch {
                    searchSources(query ?: "")
                }
                return false
            }
        })
    }

    private suspend fun searchSources(query: String?) {
        delay(300)
        newsViewModel.filterSourcesData(query ?: "")
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
        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_source_list, container, false)
    }
}


