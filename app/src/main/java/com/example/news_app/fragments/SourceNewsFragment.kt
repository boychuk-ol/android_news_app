package com.example.news_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news_app.adapters.NewsAdapter
import com.example.news_app.databinding.FragmentSourceNewsBinding
import com.example.news_app.view_models.SourceNewsViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SourceNewsFragment : Fragment() {

    private var _binding: FragmentSourceNewsBinding? = null
    private val binding get() = _binding!!
    private val sourceNewsViewModel: SourceNewsViewModel by viewModels()
    private val adapter: NewsAdapter = NewsAdapter(this)
    private val args: SourceNewsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSourceNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            sourceNews.adapter = adapter
            sourceNews.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        binding.swipeRefresh.setOnRefreshListener {
            runBlocking {
                refreshPage()
            }
        }

        if (args.source.name != sourceNewsViewModel.source?.name){
            sourceNewsViewModel.source = args.source
            sourceNewsViewModel.getNewsBySource(sourceNewsViewModel.source!!)
        }

        sourceNewsViewModel.newsList.observe(viewLifecycleOwner, Observer {
            this.adapter.changeNews(it)
            if (it.isEmpty()) {
                binding.sourceNewsHeader.text = "No news found from ${args.source.name}"
            }
            else {
                binding.sourceNewsHeader.text = sourceNewsViewModel.source?.name
            }
        })
    }

    private suspend fun refreshPage() = coroutineScope {
        val job = launch {
            sourceNewsViewModel.getNewsBySource(sourceNewsViewModel.source!!)
        }
        job.join()
        binding.swipeRefresh.isRefreshing = false
    }


    companion object {
        @JvmStatic
        fun newInstance() = SourceNewsFragment()
    }
}