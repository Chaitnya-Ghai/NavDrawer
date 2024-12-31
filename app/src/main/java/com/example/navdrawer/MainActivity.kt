package com.example.navdrawer

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.navdrawer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var appBarConfiguration:AppBarConfiguration
    lateinit var menu: Unit
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        navController=findNavController(R.id.host)//navController=findNavController(R.id.host)
        drawerLayout = findViewById(R.id.main)
        actionBarDrawerToggle=ActionBarDrawerToggle(this,drawerLayout,
            R.string.open,R.string.close)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
//        setupActionBar()
        actionBarDrawerToggle.syncState()//set automatically and sync wheter open or not
        appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout)
        setupActionBarWithNavController(navController,appBarConfiguration)
// Update drawer lock mode and toggle(shows) icon based on navigation destination
 navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.firstFragment) {
                // Show hamburger icon
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                actionBarDrawerToggle.isDrawerIndicatorEnabled = true
            } else {
                // Show back button
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)//drawer ko disable kar diya
//          abh vooh naah open / swipe hoyega + naah hamburger dikhayega  , but backbtn dikhayega
                actionBarDrawerToggle.isDrawerIndicatorEnabled = false
            }
            actionBarDrawerToggle.syncState()
        }
        binding.navView.setNavigationItemSelectedListener{
            item->
            when(item.itemId){
                R.id.item1->{
                    navController.navigate(R.id.firstFragment)
                }
                R.id.item2->{
                    navController.navigate(R.id.secondFragment)
                }
                R.id.item3->{
                    navController.navigate(R.id.threeFragment)
                }
                else->{
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            true
        }else{
            super.onOptionsItemSelected(item)
        }
    }
//  ensure the proper back navigation using NavigationUI.navigateUp().
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }

}