package com.royalit.vhire.Activitys

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.royalit.vhire.Config.Preferences
import com.royalit.vhire.Config.ViewController
import com.royalit.vhire.R
import com.royalit.vhire.databinding.ActivityDashBoardBinding

class DashBoardActivity : AppCompatActivity() {

    val binding: ActivityDashBoardBinding by lazy {
        ActivityDashBoardBinding.inflate(layoutInflater)
    }

    //bottom menu
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewController.changeStatusBarColor(
            this,
            ContextCompat.getColor(this, R.color.colorPrimary),
            false
        )

        inits()

    }

    private fun inits() {

        Preferences.saveStringValue(this@DashBoardActivity, Preferences.LOGINCHECK, "Login")

        bottomMenu()

        binding.linearNotification.setOnClickListener {
            val animations = ViewController.animation()
            binding.linearNotification.startAnimation(animations)
            val intent = Intent(this@DashBoardActivity, NotificationsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun bottomMenu() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navController = findNavController(R.id.frame_layout)
        // Setup Bottom Navigation
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true

            when (menuItem.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.jobs -> {
                    navController.navigate(R.id.jobsFragment)
                    true
                }

                R.id.applied -> {
                    navController.navigate(R.id.appliedFragment)
                    true
                }

                R.id.profile -> {
                    navController.navigate(R.id.ProfileFragment)
                    true
                }

                else -> false
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        exitDialog()
    }

    private fun exitDialog() {
        val dialogBuilder = AlertDialog.Builder(this@DashBoardActivity)
        dialogBuilder.setTitle("Exit")
        dialogBuilder.setMessage("Are you sure want to exit this app?")
        dialogBuilder.setPositiveButton("OK") { dialog, _ ->
            finishAffinity()
            dialog.dismiss()
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val b = dialogBuilder.create()
        b.show()
    }


}