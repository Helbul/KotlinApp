package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kotlinapp.databinding.ActivityMainBinding
import com.example.kotlinapp.ui.contacts.ContactsFragment
import com.example.kotlinapp.ui.favourites.FavouritesFragment
import com.example.kotlinapp.ui.moviesearch.MovieSearchFragment
import com.example.kotlinapp.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

//    private val myBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            binding.root.showSnackbar("BroadcastReceiver - CONNECTIVITY_ACTION")
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            replaceFragment(MovieSearchFragment())
        }

        val navView: BottomNavigationView = binding.navView

        navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_main -> replaceFragment(MovieSearchFragment.getInstance())
                R.id.menu_favourites -> replaceFragment(FavouritesFragment())
                R.id.menu_settings-> replaceFragment(SettingsFragment())
                R.id.menu_contacts -> replaceFragment(ContactsFragment())
            }
            true
        }

//        val intentFilter = IntentFilter()
//        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
//        registerReceiver(myBroadcastReceiver, intentFilter)


    }

    override fun onPause() {
        super.onPause()
//        unregisterReceiver(myBroadcastReceiver)
    }

    private fun replaceFragment(fragment : Fragment){
        val backStateName: String = fragment.javaClass.name
        val fragmentTag = backStateName

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0)

        if (!fragmentPopped && fragmentManager.findFragmentByTag(fragmentTag) == null) {
            fragmentTransaction.replace(R.id.container,fragment)
            fragmentTransaction.addToBackStack(backStateName)
            fragmentTransaction.commit()
        }
    }

}