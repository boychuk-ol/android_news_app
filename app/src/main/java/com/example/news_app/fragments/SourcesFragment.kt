package com.example.news_app.fragments

import android.app.Application
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.news_app.adapters.SourcesAdapter
import com.example.news_app.databinding.FragmentSourcesListBinding
import com.example.news_app.models.constants.PreferencesKeys
import com.example.news_app.view_models.SourceNewsViewModel
import com.example.news_app.view_models.SourcesViewModel
import com.example.news_app.view_models.factories.SourcesViewModelFactory
import java.util.function.LongFunction

class SourcesFragment : Fragment() {

    private var _binding: FragmentSourcesListBinding? = null
    private val binding get() = _binding!!
    private val adapter: SourcesAdapter = SourcesAdapter(this)
    private val sourcesViewModel: SourcesViewModel by viewModels {
        SourcesViewModelFactory(context?.applicationContext as Application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSourcesListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            sourcesList.adapter = adapter
            sourcesList.layoutManager = GridLayoutManager(context, 2)//LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        sourcesViewModel.sourcesList.observe(viewLifecycleOwner, Observer {
            this.adapter.changeSources(it)
            if (it.isEmpty()) {
                if (binding.sourcesInfo.visibility != View.VISIBLE) {
                    binding.sourcesInfo.visibility = View.VISIBLE
                }
                binding.sourcesInfo.text = "No sources found"
            }
            else {
                binding.sourcesInfo.visibility = View.GONE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = SourcesFragment()
    }
}
