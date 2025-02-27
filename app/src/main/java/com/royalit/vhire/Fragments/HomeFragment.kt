package com.royalit.vhire.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.models.SlideModel
import com.royalit.vhire.Activitys.ProductsDetailsActivity
import com.royalit.vhire.Activitys.ProductsListActivity
import com.royalit.vhire.Activitys.SearchActivity
import com.royalit.vhire.Adapters.HomeCategoriesAdapter
import com.royalit.vhire.Adapters.HomeProductsAdapter
import com.royalit.vhire.Config.ViewController
import com.royalit.vhire.Models.HomeCategoriesModel
import com.royalit.vhire.Models.HomeProductsModel
import com.royalit.vhire.R
import com.royalit.vhire.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    //Categories
    private lateinit var homeCategoriesAdapter: HomeCategoriesAdapter
    private lateinit var categoryList: ArrayList<HomeCategoriesModel>

    //Products
    private lateinit var homeProductsAdapter: HomeProductsAdapter
    private lateinit var productsList: ArrayList<HomeProductsModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

    }

    private fun init() {
        if (!ViewController.noInterNetConnectivity(requireActivity())) {
            ViewController.showToast(requireActivity(), "Please check your connection ")
            return
        } else {
            HomebannersApi()
            HomeCategoriesApi()
            HomeProductsApi()
        }

        binding.viewMoreProducts.setOnClickListener {
            val animations = ViewController.animation()
            binding.viewMoreProducts.startAnimation(animations)
            val intent = Intent(activity, ProductsListActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.from_right, R.anim.to_left)
        }

        binding.linearSearch.setOnClickListener {
            val animations = ViewController.animation()
            binding.linearSearch.startAnimation(animations)
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.from_right, R.anim.to_left)
        }


    }

    private fun HomebannersApi() {
        val imageList = mutableListOf<SlideModel>()
        imageList.add(SlideModel(R.drawable.dummy_banner, ""))
        imageList.add(SlideModel(R.drawable.dummy_banner, ""))
        imageList.add(SlideModel(R.drawable.dummy_banner, ""))
        binding.imageSlider.setImageList(imageList)
    }

    private fun HomeCategoriesApi() {

        // Populate the static list with data
        categoryList = ArrayList()
        categoryList.add(HomeCategoriesModel(R.drawable.vegitable_ic, "Veggies"))
        categoryList.add(HomeCategoriesModel(R.drawable.fruiots_ic, "Fruits"))
        categoryList.add(HomeCategoriesModel(R.drawable.green_leafy_ic, "leafy"))
        categoryList.add(HomeCategoriesModel(R.drawable.beets_ic, "beet"))

        // Set the adapter
        binding.recyclerViewCategories.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        homeCategoriesAdapter = HomeCategoriesAdapter(categoryList)
        binding.recyclerViewCategories.adapter = homeCategoriesAdapter
        binding.recyclerViewCategories.setHasFixedSize(true)

    }

    private fun HomeProductsApi() {

        // Populate the static list with data
        productsList = ArrayList()
        productsList.add(HomeProductsModel(R.drawable.beets_ic, "Tomato", "₹800","",4))
        productsList.add(HomeProductsModel(R.drawable.califlower_ic, "Cabbage", "₹400","",4))
        productsList.add(HomeProductsModel(R.drawable.green_leafy_ic, "Bangala", "₹500","",4))
        productsList.add(HomeProductsModel(R.drawable.beets_ic, "Capsicum", "₹800","",4))
        productsList.add(HomeProductsModel(R.drawable.califlower_ic, "Mirchi", "₹200","",4))
        productsList.add(HomeProductsModel(R.drawable.beets_ic, "Onion", "₹900","",4))
        productsList.add(HomeProductsModel(R.drawable.califlower_ic, "Carrot", "₹300","",4))

        // Set the adapter
        binding.recyclerViewProducts.layoutManager = GridLayoutManager(activity, 2)
        homeProductsAdapter = HomeProductsAdapter(productsList){ selectedItem ->
            val intent = Intent(requireActivity(), ProductsDetailsActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.from_right, R.anim.to_left)
        }

        binding.recyclerViewProducts.adapter = homeProductsAdapter
        binding.recyclerViewProducts.setHasFixedSize(true)

    }


}