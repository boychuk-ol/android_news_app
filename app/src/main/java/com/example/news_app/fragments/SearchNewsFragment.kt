package com.example.news_app.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news_app.R
import com.example.news_app.adapters.NewsAdapter
import com.example.news_app.databinding.FragmentSearchNewsBinding
import com.example.news_app.view_models.SearchNewsSharedViewModel
import com.example.news_app.view_models.factories.SearchNewsSharedViewModelFactory
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchNewsFragment : Fragment() {

    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding!!
    private val adapter: NewsAdapter = NewsAdapter(this)
    private val searchSharedViewModel: SearchNewsSharedViewModel by activityViewModels {
        SearchNewsSharedViewModelFactory(context?.applicationContext as Application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefresh.setOnRefreshListener {
            runBlocking {
                refreshPage()
            }
        }

        searchSharedViewModel.newsList.observe(viewLifecycleOwner, Observer {
            this.adapter.changeNews(it)
        })

        searchSharedViewModel.totalResults.observe(viewLifecycleOwner, Observer {
            binding.totalResults.text = "Total results: " + searchSharedViewModel.totalResults.value.toString()
        })

        binding.apply {
            news.adapter = adapter
            news.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        binding.filterButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchNewsFragment_to_newsFilterFragment2)
        }

    }

    private suspend fun refreshPage() = coroutineScope {
        val job = launch {
            searchSharedViewModel.getNewsByOptions(searchSharedViewModel.options)
        }
        job.join()
        binding.swipeRefresh.isRefreshing = false
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchNewsFragment()
    }
}