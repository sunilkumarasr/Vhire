package com.royalit.vhire.Adapters

import com.google.gson.annotations.SerializedName

data class ProductDetailsModel(
    @SerializedName("Status") val status: Boolean,
    @SerializedName("Message") val message: String,
    @SerializedName("Response") val response :ProductDetailsResponse,
    @SerializedName("code") val code: Int,
)

data class ProductDetailsResponse(
    @SerializedName("products_id") val products_id : Int,
    @SerializedName("categories_id") val categories_id : Int,
    @SerializedName("product_num") val product_num : String,
    @SerializedName("product_name") val product_name : String,
    @SerializedName("product_title") val product_title : String,
    @SerializedName("product_information") val product_information : String,
    @SerializedName("product_image") val product_image : String,
    @SerializedName("quantity") val quantity : String,
    @SerializedName("sales_price") val sales_price : String,
    @SerializedName("offer_price") val offer_price : String,
    @SerializedName("stock") val stock : String,
    @SerializedName("status") val status : Int,
    @SerializedName("order_by") val order_by : Int,
    @SerializedName("created_date") val created_date : String,
    @SerializedName("updated_date") val updated_date : String,
    @SerializedName("final_amount") val final_amount : String,
    @SerializedName("images") val productImage: List<ProductImage>,
    @SerializedName("max_order_quantity") val max_order_quantity: String?,
    @SerializedName("category_2_price") val category_2_price: String?
)

data class ProductImage(
    @SerializedName("products_id") val products_id : Int,
    @SerializedName("id") val id : Int,
    @SerializedName("image") val image : String,
    @SerializedName("fullPath") val fullPath : String,
)