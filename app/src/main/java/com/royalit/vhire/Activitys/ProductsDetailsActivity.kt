package com.royalit.vhire.Activitys

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.royalit.vhire.Adapters.Cart.CartListResponse
import com.royalit.vhire.Adapters.ProductDetailsModel
import com.royalit.vhire.Adapters.ProductDetailsResponse
import com.royalit.vhire.Api.RetrofitClient
import com.royalit.vhire.Config.Preferences
import com.royalit.vhire.Config.ViewController
import com.royalit.vhire.Models.AddtoCartResponse
import com.royalit.vhire.Models.UpdateCartResponse
import com.royalit.vhire.R
import com.royalit.vhire.databinding.ActivityProductsDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsDetailsActivity : AppCompatActivity() {

    val binding: ActivityProductsDetailsBinding by lazy {
        ActivityProductsDetailsBinding.inflate(layoutInflater)
    }

    var productResponseDetails: ProductDetailsResponse?=null

    lateinit var quantity: IntArray

    var isFavorite = false

    var productsId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewController.changeStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary), false)

        productsId = intent.getStringExtra("productsId").toString()
        quantity = intArrayOf(binding.cartQty.text.toString().toInt())

        inits()

    }


    private fun inits() {
        binding.root.findViewById<LinearLayout>(R.id.imgBack).setOnClickListener {
            val animations = ViewController.animation()
            binding.root.findViewById<LinearLayout>(R.id.imgBack).startAnimation(animations)
            finish()
        }
        
        binding.AddFavourite.setOnClickListener {
            val animations = ViewController.animation()
            binding.AddFavourite.startAnimation(animations)
            isFavorite = !isFavorite
            if (isFavorite) {
                binding.imgFav.setImageResource(R.drawable.favadd)
            } else {
                binding.imgFav.setImageResource(R.drawable.favorite_ic)
            }
        }

        binding.linearIncrement.setOnClickListener {
            val animations = ViewController.animation()
            binding.linearIncrement.startAnimation(animations)
            val cartQty = binding.cartQty.text.toString()
            if (productResponseDetails?.stock == cartQty) {
                ViewController.showToast(this@ProductsDetailsActivity,"Stock Limit only " + productResponseDetails?.stock)
            }else{
                if ((productResponseDetails?.max_order_quantity!=null)&& (productResponseDetails?.max_order_quantity!!.toInt()<=cartQty.toInt())){
                    ViewController.showToast(this@ProductsDetailsActivity,"Can't add Max Quantity for this Product" + productResponseDetails?.max_order_quantity)
                    return@setOnClickListener
                }else{
                    quantity[0]++
                    binding.cartQty.text = quantity[0].toString()
                    val cartQty1 = binding.cartQty.text.toString()
                    if (!ViewController.noInterNetConnectivity(applicationContext)) {
                        ViewController.showToast(applicationContext, "Please check your connection ")
                    } else {
                        if (cartQty1 == "1")
                            addToCartApi()
                        else{
                            upDateCartApi(cartQty1)
                        }
                    }

                }
            }

        }
        binding.linearDecrement.setOnClickListener {
            val animations = ViewController.animation()
            binding.linearDecrement.startAnimation(animations)
            if (quantity[0] > 1) {
                quantity[0]--
                binding.cartQty.text = quantity[0].toString()
                val cartQty1 = binding.cartQty.text.toString()
                upDateCartApi(cartQty1)
            }
        }

        binding.linearAddToCart.setOnClickListener {
            val animations = ViewController.animation()
            binding.linearAddToCart.startAnimation(animations)
        }

        if (!ViewController.noInterNetConnectivity(applicationContext)) {
            ViewController.showToast(applicationContext, "Please check your connection ")
        } else {
            productDetailsApi()
            getCartApi()
        }

    }

    private fun productDetailsApi() {
        binding.progressBar.visibility = View.VISIBLE
        val apiServices = RetrofitClient.apiInterface
        val call =
            apiServices.productDetailsApi(
                getString(R.string.api_key),
                productsId,
            )
        call.enqueue(object : Callback<ProductDetailsModel> {
            override fun onResponse(
                call: Call<ProductDetailsModel>,
                response: Response<ProductDetailsModel>
            ) {
                binding.progressBar.visibility = View.GONE
                try {
                    if (response.isSuccessful) {
                        productResponseDetails = response.body()?.response!!
                        DataSet(productResponseDetails!!)
                    }
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                    Log.e("onFailure",e.message.toString())
                }
            }
            override fun onFailure(call: Call<ProductDetailsModel>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Log.e("onFailure",t.message.toString())
            }
        })
    }
    private fun DataSet(productResponse: ProductDetailsResponse) {

        //out of stock
        if (productResponse.stock.toInt() ==0) {
            binding.linearOutOfStock.visibility = View.VISIBLE
            binding.linearInStock.visibility = View.GONE
            binding.linearBottom.visibility = View.GONE
        } else {
            binding.linearOutOfStock.visibility = View.GONE
            binding.linearInStock.visibility = View.VISIBLE
            binding.linearBottom.visibility = View.VISIBLE
        }

        Glide.with(binding.imgProduct)
            .load(productResponse.product_image)
            .error(R.drawable.logo)
            .into(binding.imgProduct)
        binding.txtTitle.text = productResponse.product_title
        binding.txtOfferPrice.text = "Price: "+ "\u20B9 " + productResponse.offer_price
        binding.txtActualPrice.text = "Price: "+ "\u20B9 " + productResponse.sales_price
        binding.txtActualPrice.paintFlags = binding.txtActualPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        binding.txtQuantity.text = productResponse.quantity
        binding.txtDec.text = HtmlCompat.fromHtml(productResponse.product_information, HtmlCompat.FROM_HTML_MODE_LEGACY)


    }

    private fun getCartApi() {
        val userId = Preferences.loadStringValue(applicationContext, Preferences.userId, "")
        val apiServices = RetrofitClient.apiInterface
        val call =
            apiServices.getCartApi(
                getString(R.string.api_key),
                userId.toString(),
            )
        call.enqueue(object : Callback<CartListResponse> {
            override fun onResponse(
                call: Call<CartListResponse>,
                response: Response<CartListResponse>
            ) {
                try {
                    if (response.isSuccessful) {
                        if (response.body()?.ResponseCartList?.size!! > 0) {
                            binding.cartQty.text = "0"
                            for (j in response.body()?.ResponseCartList!!.indices) {
                                if (response.body()?.ResponseCartList!!.get(j).product_id.toInt() == (productsId.toInt())) {
                                    binding.cartQty.text=response.body()?.ResponseCartList!!.get(j).cart_quantity
                                    quantity[0]=response.body()?.ResponseCartList!!.get(j).cart_quantity.toInt()
                                }
                            }
                        } else {
                            binding.cartQty.text = "0"
                        }
                    } else {
                        ViewController.showToast(applicationContext, "Invalid Mobile Number")
                    }
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                    Log.e("onFailure",e.message.toString())
                }
            }
            override fun onFailure(call: Call<CartListResponse>, t: Throwable) {
                Log.e("onFailure",t.message.toString())
            }
        })

    }

    //add to cart
    private fun addToCartApi() {
        val userId = Preferences.loadStringValue(applicationContext, Preferences.userId, "")
        val apiServices = RetrofitClient.apiInterface
        val call =
            apiServices.addToCartApi(
                getString(R.string.api_key),
                userId.toString(),
                productResponseDetails?.products_id.toString(),
                "1"
            )
        call.enqueue(object : Callback<AddtoCartResponse> {
            override fun onResponse(
                call: Call<AddtoCartResponse>,
                response: Response<AddtoCartResponse>
            ) {
                try {
                    if (response.isSuccessful) {
                        val res = response.body()
                        getCartApi()
                        if (res != null) {
                            if (res.message.equals("Success")){

                            }else{
                                ViewController.showToast(this@ProductsDetailsActivity,res.message)
                            }
                        }
                    }
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                    Log.e("onFailure",e.message.toString())
                }
            }
            override fun onFailure(call: Call<AddtoCartResponse>, t: Throwable) {
                Log.e("onFailure",t.message.toString())
            }
        })
    }

    //update Cart
    private fun upDateCartApi(cartQty: String) {
        val userId = Preferences.loadStringValue(applicationContext, Preferences.userId, "")
        val apiServices = RetrofitClient.apiInterface
        val call =
            apiServices.upDateCartApi(
                getString(R.string.api_key),
                userId.toString(),
                productResponseDetails?.products_id.toString(),
                cartQty
            )
        call.enqueue(object : Callback<UpdateCartResponse> {
            override fun onResponse(
                call: Call<UpdateCartResponse>,
                response: Response<UpdateCartResponse>
            ) {
                try {
                    if (response.isSuccessful) {
                        val res = response.body()
                        if (res != null) {
                            getCartApi()
                            if (res.message.equals("Success")){

                            }else{
                                ViewController.showToast(this@ProductsDetailsActivity,res.message)
                            }
                        }
                    }
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                    Log.e("onFailure",e.message.toString())
                }
            }
            override fun onFailure(call: Call<UpdateCartResponse>, t: Throwable) {
                Log.e("onFailure",t.message.toString())
            }
        })
    }

}