package com.example.therickandmorty.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.plusAssign
import com.example.therickandmorty.R
import com.example.therickandmorty.util.CustomNavigator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNavigation()
    }

    private fun initNavigation() {
        val navController = findNavController(R.id.navHostFragment)

        // get fragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)!!

        // setup custom navigator
        val navigator = CustomNavigator(this, navHostFragment.childFragmentManager, R.id.navHostFragment)
        navController.navigatorProvider += navigator

        // set navigation graph
        navController.setGraph(R.navigation.nav_graph_main)

    }

}