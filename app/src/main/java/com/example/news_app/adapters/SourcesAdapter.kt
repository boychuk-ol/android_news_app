package com.example.news_app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.news_app.R
import com.example.news_app.databinding.SourcesViewBinding
import com.example.news_app.fragments.SourcesFragmentDirections
import com.example.news_app.models.Source
import com.example.news_app.models.Sources


class SourcesAdapter(parentFragment: Fragment) : RecyclerView.Adapter<SourcesAdapter.SourcesHolder>() {
    private var sourcesList: ArrayList<Sources> = ArrayList()
    private var parentFragment = parentFragment

    class SourcesHolder(item: View, parentFragment: Fragment): RecyclerView.ViewHolder(item) {
        private val binding = SourcesViewBinding.bind(item)
        private val parentFragment: Fragment = parentFragment
        private val navController = parentFragment.findNavController()
        fun bind(source: Sources) = with(binding) {
            textView2.text = source.name
            itemView.setOnClickListener {
                val directions = SourcesFragmentDirections.actionSourcesFragmentToSourceNewsFragment(Source(source.id, source.name))
                navController.navigate(directions)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourcesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sources_view, parent, false)
        return SourcesHolder(view, parentFragment)
    }

    override fun getItemCount(): Int {
        return sourcesList.size
    }

    override fun onBindViewHolder(holder: SourcesHolder, position: Int) {
        holder.bind(sourcesList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeSources(sources: ArrayList<Sources>) {
        sourcesList = sources
        notifyDataSetChanged()
    }

}