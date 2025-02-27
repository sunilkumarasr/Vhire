package com.royalit.vhire.Activitys

import android.app.Dialog
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.royalit.vhire.Adapters.AddressAdapter
import com.royalit.vhire.Config.ViewController
import com.royalit.vhire.Models.AddressModel
import com.royalit.vhire.R
import com.royalit.vhire.databinding.ActivityPlaceOrderBinding

class CheckOutActivity : AppCompatActivity() {

    val binding: ActivityPlaceOrderBinding by lazy {
        ActivityPlaceOrderBinding.inflate(layoutInflater)
    }

    //Address
    private lateinit var addressAdapter: AddressAdapter
    private lateinit var addressList: ArrayList<AddressModel>

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
        binding.root.findViewById<TextView>(R.id.txtTitle).text = getString(R.string.checkout)
        binding.root.findViewById<LinearLayout>(R.id.imgBack).setOnClickListener {
            val animations = ViewController.animation()
            binding.root.findViewById<LinearLayout>(R.id.imgBack).startAnimation(animations)
            finish()
        }
        binding.root.findViewById<LinearLayout>(R.id.linearCart).visibility = View.INVISIBLE

        binding.txtAddAddress.setOnClickListener {
            addAddressDialog()
        }
        binding.linearSubmit.setOnClickListener {
            orderSuccessPopup()
            // orderFailedPopup()
        }

        addressListApi()
    }

    private fun addressListApi() {

        // Populate the static list with data
        addressList = ArrayList()
        addressList.add(AddressModel("Home", "House 10, Road 5, Block J, Baridhara", "534146"))
        addressList.add(AddressModel("Office", "Road 5, Block J, Baridhara", "534211"))


        // Set the adapter
        binding.recyclerview.layoutManager =
            LinearLayoutManager(this@CheckOutActivity, LinearLayoutManager.VERTICAL, false)
        addressAdapter = AddressAdapter(addressList) { selectedItem, type ->

        }
        binding.recyclerview.adapter = addressAdapter
        binding.recyclerview.setHasFixedSize(true)

    }


    private fun addAddressDialog() {
        val bottomSheetDialog = BottomSheetDialog(this@CheckOutActivity)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_addaddress, null)
        bottomSheetDialog.setContentView(view)

        val linearSubmit = view.findViewById<LinearLayout>(R.id.linearSubmit)

        linearSubmit.setOnClickListener {
            val animations = ViewController.animation()
            linearSubmit.startAnimation(animations)
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

    private fun orderSuccessPopup() {
        val dialog = Dialog(this)
        val customView = LayoutInflater.from(this).inflate(R.layout.payment_success_popup, null)
        dialog.setContentView(customView)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val layoutParams = dialog.window?.attributes
        layoutParams?.width =
            (resources.displayMetrics.widthPixels * 0.9).toInt() // 90% of screen width
        dialog.window?.attributes = layoutParams

        //set Sound
        var mediaPlayer: MediaPlayer? = null
        mediaPlayer = MediaPlayer.create(this, R.raw.success_file)
        mediaPlayer?.start()

        val linearSubmit = customView.findViewById<LinearLayout>(R.id.linearSubmit)
        linearSubmit.setOnClickListener {
            val animations = ViewController.animation()
            linearSubmit.startAnimation(animations)
            dialog.dismiss()
        }

        // Show the dialog
        dialog.show()
    }

    private fun orderFailedPopup() {
        val dialog = Dialog(this)
        val customView = LayoutInflater.from(this).inflate(R.layout.payment_failed_popup, null)
        dialog.setContentView(customView)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val layoutParams = dialog.window?.attributes
        layoutParams?.width =
            (resources.displayMetrics.widthPixels * 0.9).toInt() // 90% of screen width
        dialog.window?.attributes = layoutParams

        val linearSubmit = customView.findViewById<LinearLayout>(R.id.linearSubmit)

        linearSubmit.setOnClickListener {
            dialog.dismiss()
        }

        // Show the dialog
        dialog.show()
    }


}