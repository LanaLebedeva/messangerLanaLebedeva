package com.github.lanalebedeva.mydiscord


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.lanalebedeva.mydiscord.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding: ActivityMainBinding by viewBinding(R.id.main_activity_cl)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_activity_fragment_container_view) as NavHostFragment?
            ?: return
        val navController = host.navController
        setupBottomNavMenu(navController)
        (application as App).appComponent.inject(this)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.main_activity_bottom_navigation)
        bottomNav.setupWithNavController(navController)
    }
}
