package com.royalit.vhire.Logins

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.royalit.vhire.Config.ViewController
import com.royalit.vhire.R
import com.royalit.vhire.databinding.ActivityCreatePasswordBinding

class CreatePasswordActivity : AppCompatActivity() {

    val binding: ActivityCreatePasswordBinding by lazy {
        ActivityCreatePasswordBinding.inflate(layoutInflater)
    }

    lateinit var email:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewController.changeStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary), false)
        email= intent.getStringExtra("email").toString()


        inits()

    }

    private fun inits() {
        binding.linearBack.setOnClickListener {
            val animations = ViewController.animation()
            binding.linearBack.startAnimation(animations)
            val intent = Intent(this@CreatePasswordActivity, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.from_left, R.anim.to_right)
        }

//
//        binding.cardLogin.setOnClickListener {
//            if(!ViewController.noInterNetConnectivity(applicationContext)){
//                ViewController.showToast(applicationContext, "Please check your connection ")
//            }else{
//                createApi()
//            }
//        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@CreatePasswordActivity, LoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.from_left, R.anim.to_right)
    }


}