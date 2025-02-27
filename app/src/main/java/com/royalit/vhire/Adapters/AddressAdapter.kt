package com.royalit.vhire.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.royalit.vhire.Models.AddressModel
import com.royalit.vhire.R

class AddressAdapter(private val itemList: ArrayList<AddressModel>, private val onClick: (AddressModel, String) -> Unit) : RecyclerView.Adapter<AddressAdapter.ItemViewHolder>() {

    // ViewHolder class to hold the views for each item
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        val txtAddress: TextView = itemView.findViewById(R.id.txtAddress)
        val txtPinCode: TextView = itemView.findViewById(R.id.txtPinCode)
        val txtMobile: TextView = itemView.findViewById(R.id.txtMobile)
        val imgEdit: ImageView = itemView.findViewById(R.id.imgEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.address_items_list, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.txtTitle.text = item.title
        holder.txtAddress.text = item.address
        holder.txtPinCode.text = item.pincode

        holder.imgEdit.setOnClickListener {
            onClick(item,"")
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}