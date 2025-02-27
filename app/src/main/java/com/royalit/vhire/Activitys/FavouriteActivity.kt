package com.royalit.vhire.Activitys

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.royalit.vhire.Config.ViewController
import com.royalit.vhire.R
import com.royalit.vhire.databinding.ActivityFavouriteBinding

class FavouriteActivity : AppCompatActivity() {

    val binding: ActivityFavouriteBinding by lazy {
        ActivityFavouriteBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewController.changeStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary), false)

        inits()

    }

    private fun inits() {
        binding.root.findViewById<TextView>(R.id.txtTitle).text = getString(R.string.favorite)
        binding.root.findViewById<LinearLayout>(R.id.imgBack).setOnClickListener {
            val animations = ViewController.animation()
            binding.root.findViewById<LinearLayout>(R.id.imgBack).startAnimation(animations)
            finish()
        }


    }

}