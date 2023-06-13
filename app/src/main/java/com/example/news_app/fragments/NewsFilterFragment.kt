package com.example.news_app.fragments

import android.app.Application
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.news_app.R
import com.example.news_app.databinding.FragmentNewsFilterBinding
import com.example.news_app.models.FilterOptions
import com.example.news_app.models.constants.NewsFilterConstants
import com.example.news_app.view_models.SearchNewsSharedViewModel
import com.example.news_app.view_models.factories.SearchNewsSharedViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.coroutineScope
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.Duration.Companion.days

class NewsFilterFragment : DialogFragment() {

    private var _binding: FragmentNewsFilterBinding? = null
    private val binding get() = _binding!!
    private val searchSharedViewModel: SearchNewsSharedViewModel by activityViewModels {
        SearchNewsSharedViewModelFactory(context?.applicationContext as Application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsFilterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.fragmentLayout) as NavHostFragment
        //val navController = navHostFragment.navController
        //val searchNewsFragment = navHostFragment.childFragmentManager.fragments[0]

        val languageValues = this.resources.getStringArray(R.array.language_values)
        val sortByValues = this.resources.getStringArray(R.array.sort_by_values)

        setSelection(searchSharedViewModel.options, languageValues, sortByValues)

        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setSelection(androidx.core.util.Pair(
                    searchSharedViewModel.options.dateFrom?.atStartOfDay(ZoneId.systemDefault())?.toEpochSecond()!! * 1000,
                    searchSharedViewModel.options.dateTo?.atStartOfDay(ZoneId.systemDefault())?.toEpochSecond()!! * 1000))
                .setTitleText("Select date range")
                .build()

        val dateFrom = searchSharedViewModel.options.dateFrom!!.toString()
        val dateTo  = searchSharedViewModel.options.dateTo!!.toString()

        binding.dateInterval.text = "$dateFrom - $dateTo"

        binding.dateChange.setOnClickListener {
            dateRangePicker.show(childFragmentManager, "DATE_RANGE_PICKER")
            dateRangePicker.addOnPositiveButtonClickListener {
                binding.dateInterval.text = dateRangePicker.headerText
            }
        }

        binding.cancelButton.setOnClickListener {
            this.dismiss()
        }

        binding.setDefaultButton.setOnClickListener {
            searchSharedViewModel.options.setDefault()
            setSelection(searchSharedViewModel.options, languageValues, sortByValues)
            val dateFrom = searchSharedViewModel.options.dateFrom
            val dateTo  = searchSharedViewModel.options.dateTo
            binding.dateInterval.text = "$dateFrom - $dateTo"
        }

        binding.applyButton.setOnClickListener {
            val languagePosition = binding.language.selectedItemPosition
            val sortByPosition = binding.sortBy.selectedItemPosition
            val language = languageValues[languagePosition]
            val sortBy = sortByValues[sortByPosition]
            val source = binding.source.text.toString()
            val query = binding.query.text.toString()
            val dateFromSelected =  LocalDateTime.ofInstant(Instant.ofEpochMilli(dateRangePicker.selection?.first!!), TimeZone.getDefault().toZoneId()).toLocalDate()
            val dateToSelected = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateRangePicker.selection?.second!!), TimeZone.getDefault().toZoneId()).toLocalDate()
            if (!searchSharedViewModel.options.equals(FilterOptions(language, sortBy, source, query, dateFromSelected, dateToSelected))) {
                searchSharedViewModel.options = FilterOptions(language, sortBy, source, query, dateFromSelected, dateToSelected)
                searchSharedViewModel.getNewsByOptions(searchSharedViewModel.options)
            }
            findNavController().navigate(R.id.action_newsFilterDialogFragment_to_searchNewsFragment)
        }

    }

    private fun setSelection(options: FilterOptions, languageValues: Array<String>, sortByValues: Array<String>) {
        binding.language.setSelection(languageValues.indexOf(searchSharedViewModel.options.language))
        binding.sortBy.setSelection(sortByValues.indexOf(searchSharedViewModel.options.sortBy))
        binding.source.setText(searchSharedViewModel.options.source)
        binding.query.setText(searchSharedViewModel.options.query)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsFilterFragment()
    }

}