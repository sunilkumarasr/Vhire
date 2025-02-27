package com.royalit.vhire.Logins

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.royalit.vhire.Activitys.DashBoardActivity
import com.royalit.vhire.Api.RetrofitClient
import com.royalit.vhire.Config.Preferences
import com.royalit.vhire.Config.ViewController
import com.royalit.vhire.Models.LoginModel
import com.royalit.vhire.R
import com.royalit.vhire.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    lateinit var token: String

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

        binding.txtForgot.setOnClickListener {
            val animations = ViewController.animation()
            binding.txtForgot.startAnimation(animations)
            val intent = Intent(this@LoginActivity, ForgotActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.from_right, R.anim.to_left)
        }
        binding.registerLinear.setOnClickListener {
            val animations = ViewController.animation()
            binding.registerLinear.startAnimation(animations)
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.from_right, R.anim.to_left)
        }
        binding.linearSubmit.setOnClickListener {
            if (!ViewController.noInterNetConnectivity(applicationContext)) {
                ViewController.showToast(applicationContext, "Please check your connection ")
            } else {
                loginApi()
            }
        }

    }


    private fun loginApi() {
        val phone = binding.phoneEdit.text?.trim().toString()
        val password = binding.passwordEdit.text?.trim().toString()

        ViewController.hideKeyBoard(this@LoginActivity )

        val intent = Intent(this@LoginActivity, DashBoardActivity::class.java)
        startActivity(intent)
        finish()


//        if (phone.isEmpty()) {
//            ViewController.showToast(applicationContext, "Enter Email")
//            return
//        }
//        if (password.isEmpty()) {
//            ViewController.showToast(applicationContext, "Enter password")
//            return
//        }
//
//        if (!ViewController.validateMobile(phone)) {
//            ViewController.showToast(applicationContext, "Enter Valid mobile number")
//        } else {
//            ViewController.showLoading(this@LoginActivity)
//
//            val apiServices = RetrofitClient.apiInterface
//            val call =
//                apiServices.loginApi(
//                    getString(R.string.api_key),
//                    phone,
//                    password, "token"
//                )
//
//            call.enqueue(object : Callback<LoginModel> {
//                override fun onResponse(
//                    call: Call<LoginModel>,
//                    response: Response<LoginModel>
//                ) {
//                    ViewController.hideLoading()
//                    try {
//                        if (response.isSuccessful) {
//
//                            if (response.body()?.code==1){
//                                Preferences.saveStringValue(this@LoginActivity, Preferences.userId,response.body()?.response!!.customer_id.toString())
//                                Preferences.saveStringValue(this@LoginActivity, Preferences.name,response.body()?.response!!.full_name.toString())
//                                Preferences.saveStringValue(this@LoginActivity, Preferences.mobileNumber,response.body()?.response!!.mobile_number.toString())
//                                Preferences.saveStringValue(this@LoginActivity, Preferences.address,response.body()?.response!!.address.toString())
//                                Preferences.saveStringValue(this@LoginActivity, Preferences.email,response.body()?.response!!.email_id.toString())
//
//                                val intent = Intent(this@LoginActivity, DashBoardActivity::class.java)
//                                startActivity(intent)
//                                finish()
//                            }else {
//                                ViewController.showToast(applicationContext, "Login Failed")
//                            }
//
//
//                        } else {
//                            ViewController.showToast(applicationContext, "Invalid Mobile Number")
//                        }
//                    } catch (e: NullPointerException) {
//                        e.printStackTrace()
//                    }
//                }
//
//                override fun onFailure(call: Call<LoginModel>, t: Throwable) {
//                    ViewController.hideLoading()
//                    ViewController.showToast(applicationContext, "Invalid Credentials")
//                }
//            })
//
//        }


    }


    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

}