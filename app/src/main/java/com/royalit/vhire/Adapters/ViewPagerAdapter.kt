package com.royalit.vhire.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.royalit.vhire.Fragments.MyOrders.AllOrdersFragment
import com.royalit.vhire.Fragments.MyOrders.CompleteOrdersFragment
import com.royalit.vhire.Fragments.MyOrders.PendingOrdersFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3 // Number of fragments

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllOrdersFragment()
            1 -> PendingOrdersFragment()
            2 -> CompleteOrdersFragment()
            else -> AllOrdersFragment()
        }
    }
}