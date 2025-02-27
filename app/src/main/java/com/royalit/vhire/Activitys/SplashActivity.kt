package com.royalit.vhire.Activitys

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.royalit.vhire.Config.Preferences
import com.royalit.vhire.Config.ViewController
import com.royalit.vhire.R
import com.royalit.vhire.databinding.ActivitySplashBinding
import java.util.Locale

class SplashActivity : AppCompatActivity() {

    val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewController.changeStatusBarColor(
            this,
            ContextCompat.getColor(this, R.color.splashcolor),
            false
        )

        inits()

    }

    private fun inits() {
        
        //language
//        val languageCode = Preferences.loadStringValue(applicationContext, Preferences.languageCode, "")
//        if (languageCode != null) {
//            setLocale(languageCode)
//        }

        val loginCheck = Preferences.loadStringValue(applicationContext, Preferences.LOGINCHECK, "")
        Handler(Looper.getMainLooper()).postDelayed({
            if (loginCheck.equals("Login")) {
                val intent = Intent(this@SplashActivity, DashBoardActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashActivity, IntroScreensActivity::class.java)
                startActivity(intent)
            }
        }, 3000)
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
    }



}