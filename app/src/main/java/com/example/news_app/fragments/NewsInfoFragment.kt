package com.example.news_app.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.news_app.R
import com.example.news_app.activities.MainActivity
import com.example.news_app.databinding.FragmentNewsInfoBinding
import com.example.news_app.databinding.FragmentSearchNewsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso

class NewsInfoFragment : Fragment() {

    private var _binding: FragmentNewsInfoBinding? = null
    private val binding get() = _binding!!
    private val args: NewsInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val activity = activity as MainActivity
        activity.setBottomNavigationVisibility(View.INVISIBLE)
        activity.showActionBar(true)
        activity.actionBar?.show()
        _binding = FragmentNewsInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.get().load(args.info.urlToImage).into(binding.imageInfo)
        binding.author.text = args.info.author
        binding.date.text = args.info.publishedAt
        binding.title.text = args.info.title
        if (args.info.content != null) {
            binding.content.text = args.info.content
        } else {
            binding.content.text = args.info.description
        }
        binding.link.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(args.info.url))
            startActivity(browserIntent)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        val activity = activity as MainActivity
        activity.setBottomNavigationVisibility(View.VISIBLE)
        activity.showActionBar(false)
    }


    companion object {
        @JvmStatic
        fun newInstance() = NewsInfoFragment()
    }
}