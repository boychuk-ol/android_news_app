package com.example.news_app.activities

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import androidx.preference.PreferenceManager
import com.example.news_app.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.news_app.R
import com.example.news_app.models.constants.PreferencesKeys
import com.example.news_app.view_models.TopNewsViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        showActionBar(false)
        setContentView(binding.root)

        navController = (supportFragmentManager.findFragmentById(R.id.fragmentLayout) as NavHostFragment).navController
        val navView: BottomNavigationView = findViewById(R.id.bottomNavig)
        navView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.topNews, R.id.searchNewsFragment,  R.id.sourcesFragment, R.id.settings)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    fun setBottomNavigationVisibility(visibility: Int) {
        binding.bottomNavig.visibility = visibility
    }

    fun showActionBar(showActionBar: Boolean) {
        if (showActionBar) {
            supportActionBar?.show()
        } else {
            supportActionBar?.hide()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }


}