package com.royalit.vhire.Logins

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.royalit.vhire.Activitys.DashBoardActivity
import com.royalit.vhire.Api.RetrofitClient
import com.royalit.vhire.Config.Preferences
import com.royalit.vhire.Config.ViewController
import com.royalit.vhire.Models.RegisterModel
import com.royalit.vhire.R
import com.royalit.vhire.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewController.changeStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary), false)

        inits()
    }

    private fun inits() {
        binding.loginLinear.setOnClickListener {
            val animations = ViewController.animation()
            binding.loginLinear.startAnimation(animations)
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.from_left, R.anim.to_right)
        }
        binding.linearSubmit.setOnClickListener {
            val animations = ViewController.animation()
            binding.linearSubmit.startAnimation(animations)

            if (!ViewController.noInterNetConnectivity(applicationContext)) {
                ViewController.showToast(applicationContext, "Please check your connection ")
            } else {
                registerApi()
            }


        }
    }


    private fun registerApi() {
        val name = binding.nameEdit.text?.trim().toString()
        val email = binding.emailEdit.text?.trim().toString()
        val mobile = binding.mobileEdit.text?.trim().toString()
        val password = binding.passwordEdit.text?.trim().toString()

        ViewController.hideKeyBoard(this@RegisterActivity )

        if (name.isEmpty()) {
            ViewController.showToast(applicationContext, "Enter Your Name")
            return
        }
        if (email.isEmpty()) {
            ViewController.showToast(applicationContext, "Enter Email")
            return
        }
        if (mobile.isEmpty()) {
            ViewController.showToast(applicationContext, "Enter Email")
            return
        }
        if (password.isEmpty()) {
            ViewController.showToast(applicationContext, "Enter password")
            return
        }

        if (!ViewController.validateMobile(mobile)) {
            ViewController.showToast(applicationContext, "Enter Valid mobile number")
        } else {
            ViewController.showLoading(this@RegisterActivity)

            val apiServices = RetrofitClient.apiInterface
            val call =
                apiServices.registerApi(
                    getString(R.string.api_key),
                    name,
                    mobile,
                    email,
                    "",
                    "",
                    password,
                    ""
                )

            call.enqueue(object : Callback<RegisterModel> {
                override fun onResponse(
                    call: Call<RegisterModel>,
                    response: Response<RegisterModel>
                ) {
                    binding.progressBar.visibility = View.GONE
                    try {
                        if (response.isSuccessful) {

                            if (response.body()?.code==1){
                                Preferences.saveStringValue(this@RegisterActivity, Preferences.userId,response.body()?.response!!.user_id.toString())
                                Preferences.saveStringValue(this@RegisterActivity, Preferences.name,response.body()?.response!!.full_name.toString())
                                Preferences.saveStringValue(this@RegisterActivity, Preferences.mobileNumber,response.body()?.response!!.mobile_number.toString())
                                Preferences.saveStringValue(this@RegisterActivity, Preferences.address,response.body()?.response!!.address.toString())
                                Preferences.saveStringValue(this@RegisterActivity, Preferences.email,response.body()?.response!!.email_id.toString())

                                val intent = Intent(this@RegisterActivity, DashBoardActivity::class.java)
                                startActivity(intent)
                                finish()
                            }else {
                                ViewController.showToast(applicationContext, "Register Failed")
                            }


                        } else {
                            ViewController.showToast(applicationContext, "Register Failed")
                        }
                    } catch (e: NullPointerException) {
                        e.printStackTrace()
                    } catch (e: TypeCastException) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<RegisterModel>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    ViewController.showToast(applicationContext, "Register Failed")
                }
            })

        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.from_left, R.anim.to_right)
    }

}