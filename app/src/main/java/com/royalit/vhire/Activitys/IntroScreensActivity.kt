package com.royalit.vhire.Activitys

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.royalit.vhire.Adapters.IntroSliderAdapter
import com.royalit.vhire.Config.ViewController
import com.royalit.vhire.Logins.LoginActivity
import com.royalit.vhire.Models.IntroSlide
import com.royalit.vhire.R
import com.royalit.vhire.databinding.ActivityIntroScreensBinding

class IntroScreensActivity : AppCompatActivity() {

    val binding: ActivityIntroScreensBinding by lazy {
        ActivityIntroScreensBinding.inflate(layoutInflater)
    }

    private val introSliderAdapter = IntroSliderAdapter(
        listOf(
            IntroSlide(
                "Best Platform for Job Hunter",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text",
                R.drawable.logo1
            ),
            IntroSlide(
                "Explore your dream job with ease",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text",
                R.drawable.logo2
            ),
            IntroSlide(
                "Get the good job with Us!",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text",
                R.drawable.logo3
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewController.changeStatusBarColor(
            this,
            ContextCompat.getColor(this, R.color.white),
            false
        )

        inits()


    }


    private fun inits() {
        binding.introSliderViewPager.adapter = introSliderAdapter
        setupIndicators()
        setCurrentIndicator(0)

        binding.introSliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)

                // Change buttons dynamically
                if (position == introSliderAdapter.itemCount - 1) {
                    binding.relativeBottom.visibility = View.GONE
                    binding.linearSubmit.visibility = View.VISIBLE
                } else {
                    binding.relativeBottom.visibility = View.VISIBLE
                    binding.linearSubmit.visibility = View.GONE
                }
            }
        })

        binding.txtNext.setOnClickListener {
            if (binding.introSliderViewPager.currentItem + 1 < introSliderAdapter.itemCount) {
                binding.introSliderViewPager.currentItem += 1
            } else {
//                Intent(applicationContext, LoginActivity::class.java).also {
//                    startActivity(it)
//                    finish()
//                }
            }
        }

        binding.txtSkip.setOnClickListener {
//            Intent(applicationContext, LoginActivity::class.java).also {
//                startActivity(it)
//                finish()
//            }
        }
        binding.linearSubmit.setOnClickListener {
//            Intent(applicationContext, LoginActivity::class.java).also {
//                startActivity(it)
//                finish()
//            }
        }
    }



    private fun setupIndicators() {
        binding.indicatorContainer.removeAllViews() // Clear previous indicators

        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
            setMargins(8, 0, 8, 0)
        }

        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext).apply {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this.layoutParams = layoutParams
            }

            binding.indicatorContainer.addView(indicators[i])
        }
    }


    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.indicatorContainer.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorContainer.getChildAt(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }

}