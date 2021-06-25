package com.example.therickandmorty.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.therickandmorty.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Dynamic ActionBar title from a Fragment using AndroidX Navigation
         */
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
                title = when (destination.id) {
                    R.id.characterDetailFragment -> "Character"
                    R.id.episodeFragment -> "Episode"
                    R.id.locationFragment -> "Location"
                    else -> "The Rick And Morty"
                }
        }

    }
}