package com.example.momoexample.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.momoexample.R
import com.example.momoexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindingView: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        bindingView = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(bindingView.root)
        setSystemBarPadding()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp() = Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()

    private fun setSystemBarPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(bindingView.root) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
                        or WindowInsetsCompat.Type.displayCutout()
            )
            v.updatePadding(
                left = bars.left,
                top = bars.top,
                right = bars.right,
                bottom = bars.bottom,
            )
            WindowInsetsCompat.CONSUMED
        }
    }
}
