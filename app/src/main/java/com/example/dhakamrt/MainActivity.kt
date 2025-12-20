package com.example.dhakamrt

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dhakamrt.databinding.ActivityMainBinding
import com.example.dhakamrt.ui.fare.FareFragment
import com.example.dhakamrt.ui.history.HistoryFragment
import com.example.dhakamrt.ui.home.HomeFragment
import com.example.dhakamrt.ui.more.MoreFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Default screen = Home
        loadFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_fare -> loadFragment(FareFragment())
                R.id.menu_balance -> loadFragment(HomeFragment())
                R.id.menu_history -> loadFragment(HistoryFragment())
                R.id.menu_more -> loadFragment(MoreFragment())
            }
            true
        }
    }

    /**
     * This method receives NFC intents
     * and forwards them to HomeFragment
     */
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (currentFragment is HomeFragment) {
            currentFragment.onNfcIntent(intent)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
