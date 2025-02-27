package com.royalit.vhire.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.royalit.vhire.Adapters.Cart.CartItems
import com.royalit.vhire.Api.RetrofitClient
import com.royalit.vhire.R

class CartAdapter(
    val context: Context,
    private val items: List<CartItems>,
    var click: ProductItemClick?,
    var quantityChangeListener: CartItemQuantityChangeListener?
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProducts: ImageView = itemView.findViewById(R.id.imgProducts)
        val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        val txtOfferPrice: TextView = itemView.findViewById(R.id.txtOfferPrice)
        val txtTotalPrice: TextView = itemView.findViewById(R.id.txtTotalPrice)
        val txtQuantity: TextView = itemView.findViewById(R.id.txtQuantity)
        val cartQty: TextView = itemView.findViewById(R.id.cartQty)
        val linearDecrement: LinearLayout = itemView.findViewById(R.id.linearDecrement)
        val linearIncrement: LinearLayout = itemView.findViewById(R.id.linearIncrement)
        val imgDelete: ImageView = itemView.findViewById(R.id.imgDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_items_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        Glide.with(holder.imgProducts)
            .load(RetrofitClient.Image_URL2+item.product_image)
            .error(R.drawable.logo)
            .into(holder.imgProducts)
        holder.txtTitle.text = item.product_title
        holder.txtQuantity.text = item.quantity
        holder.txtOfferPrice.text =  "(Price : ₹"+item.offer_price+" )"
        holder.cartQty.text = item.cart_quantity

        val finalAmount: Int = item.offer_price.toInt() * item.cart_quantity.toInt()
        holder.txtTotalPrice.text = "Quantity : "+item.cart_quantity+" = Total : ₹ "+finalAmount


        holder.imgDelete.setOnClickListener {
            quantityChangeListener?.onDeleteCartItem(item)
        }

        holder.linearDecrement.setOnClickListener {

        }
        holder.linearIncrement.setOnClickListener {

        }
        holder.imgProducts.setOnClickListener {

        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface ProductItemClick {
        fun onProductItemClick(itemsData: CartItems?)
        fun onAddToCartClicked(itemsData: CartItems?, cartQty: String?,isAdd:Int)
    }

    interface CartItemQuantityChangeListener {
        fun onQuantityChanged(cartItem: CartItems, newQuantity: Int)
        fun onDeleteCartItem(cartItem: CartItems)
    }


}