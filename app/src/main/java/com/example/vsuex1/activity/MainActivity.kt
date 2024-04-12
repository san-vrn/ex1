package com.example.vsuex1.activity

import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.vsuex1.R
import com.example.vsuex1.databinding.ActivityMainBinding
import com.example.vsuex1.receiver.CustomBroadcastListenerReceiver


private const val SURF_RECEIVER_KEY = "SURF_RECEIVER_KEY"


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val surfActionBroadcastListenerReceiver = CustomBroadcastListenerReceiver()
    private var surfGlobalMessage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)


         val surfActionBroadcastListenerFilter = IntentFilter("ru.shalkoff.vsu_lesson2_2024.SURF_ACTION")
         registerReceiver(surfActionBroadcastListenerReceiver,surfActionBroadcastListenerFilter)
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(surfActionBroadcastListenerReceiver)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val localSurfMessage = surfActionBroadcastListenerReceiver.getSurfMessageValue()
        if(!localSurfMessage.isNullOrEmpty()){surfGlobalMessage = localSurfMessage }
        outState.putString(SURF_RECEIVER_KEY,surfGlobalMessage)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val secretKeyInstanceState = savedInstanceState?.getString(SURF_RECEIVER_KEY)
        if (secretKeyInstanceState!=null) {
            surfGlobalMessage = secretKeyInstanceState
            Log.i(SURF_RECEIVER_KEY, surfGlobalMessage)
        }
    }

}