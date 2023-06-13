package com.example.news_app.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.news_app.NavGraphDirections
import com.example.news_app.R
import com.example.news_app.databinding.NewsViewBinding
import com.example.news_app.models.NewsHeadlines
import com.squareup.picasso.Picasso

class NewsAdapter(parentFragment: Fragment) : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {
    private var newsList = ArrayList<NewsHeadlines>()
    private var parentFragment = parentFragment

    class NewsHolder(item: View, parentFragment: Fragment): RecyclerView.ViewHolder(item) {
        private val binding = NewsViewBinding.bind(item)
        private val parentFragment: Fragment = parentFragment
        private val navController = parentFragment.findNavController()
        fun bind(news: NewsHeadlines) = with(binding) {
            Picasso.get().load(news.urlToImage).into(image)
            if (news.source?.name != null) {
                source.text = news.source.name
            } else {
                source.text = news.author
            }
            date.text = news.publishedAt
            title.text = news.title
            itemView.setOnClickListener {
                val newsHeadlines = NewsHeadlines(news.source, news.author, news.title, news.description, news.url, news.urlToImage, news.publishedAt, news.content)
                val directions = NavGraphDirections.actionGlobalNewsInfoFragment(newsHeadlines)
                navController.navigate(directions)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_view, parent, false)
        return NewsHolder(view, parentFragment)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(newsList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeNews(news: ArrayList<NewsHeadlines>) {
        newsList = news
        notifyDataSetChanged()
    }

}