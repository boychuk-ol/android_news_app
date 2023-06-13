package com.example.news_app.fragments

import android.app.Application
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news_app.R
import com.example.news_app.adapters.NewsAdapter
import com.example.news_app.databinding.FragmentTopNewsBinding
import com.example.news_app.view_models.TopNewsViewModel
import com.example.news_app.view_models.factories.TopNewsViewModelFactory
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.*

class TopNewsFragment : Fragment() {

    private var _binding: FragmentTopNewsBinding? = null
    private val binding get() = _binding!!
    private val adapter: NewsAdapter = NewsAdapter(this)
    private val topNewsViewModel: TopNewsViewModel by viewModels {
        TopNewsViewModelFactory(context?.applicationContext as Application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = binding.tabLayout

        if (savedInstanceState != null) {
            val tabPosition = savedInstanceState.getInt("tab_position")
            tabLayout.selectTab(tabLayout.getTabAt(tabPosition))
        } else if (topNewsViewModel.tabPosition != 0) {
            tabLayout.selectTab(tabLayout.getTabAt(topNewsViewModel.tabPosition))
        }

        binding.swipeRefresh.setOnRefreshListener {
            runBlocking {
                refreshPage()
            }
        }

        binding.apply {
            newsList.adapter = adapter
            newsList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        binding.search.setOnQueryTextListener( object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                topNewsViewModel.getNews(topNewsViewModel.country, topNewsViewModel.category, topNewsViewModel.query)
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                topNewsViewModel.query = p0
                return true
            }
        })

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                topNewsViewModel.category = tab?.text.toString()
                topNewsViewModel.getNews(topNewsViewModel.country, topNewsViewModel.category, topNewsViewModel.query)
                tab?.select()
                topNewsViewModel.tabPosition = binding.tabLayout.selectedTabPosition
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        topNewsViewModel.newsList.observe(viewLifecycleOwner, Observer {
            this.adapter.changeNews(it)
        })

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("tab_position", topNewsViewModel.tabPosition)
    }

    companion object {
        @JvmStatic
        fun newInstance() = TopNewsFragment()
    }

    private suspend fun refreshPage() = coroutineScope {
        val job = launch {
            topNewsViewModel.getNews(
                topNewsViewModel.country,
                topNewsViewModel.category,
                topNewsViewModel.query
            )
        }
        job.join()
        binding.swipeRefresh.isRefreshing = false
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}