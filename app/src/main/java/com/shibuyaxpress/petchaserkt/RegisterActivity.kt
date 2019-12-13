package com.shibuyaxpress.petchaserkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_register.*

private const val NUM_PAGES = 5

class RegisterActivity : AppCompatActivity() {

    private lateinit var mPager: ViewPager
    private var currentItem = 0
    private var fragList = ArrayList<Fragment>()
    private var buttonNEXT: MaterialButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //set fragments for list frag
        fragList.add(RegisterNameFragment())
        fragList.add(RegisterEmailFragment())
        fragList.add(RegisterBirthdayFragment())
        fragList.add(RegisterAddressFragment())
        fragList.add(RegisterContactPhoneFragment())

        //set initial progress bar value
        loginProgressBar.progress = 100 / (fragList.size + 1)
        //initiate a ViewPager and a PageAdapter
        mPager = findViewById(R.id.pager)
        //initiate button
        buttonNEXT = findViewById(R.id.nextButton)

        //the page adapter, who provides the pages to the view adapter widget
        val pageAdapter = ScreenSlidePageAdapter(supportFragmentManager)
        mPager.adapter = pageAdapter

        buttonNEXT!!.setOnClickListener {
            Toast.makeText(this@RegisterActivity, "$currentItem", Toast.LENGTH_SHORT).show()
            if (currentItem < fragList.size) {
                currentItem+=1
            mPager.setCurrentItem(currentItem, true)
            }
        }
        mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                /*Toast.makeText(applicationContext,"$position",Toast.LENGTH_SHORT).show()
                when(position) {
                    0 -> {
                        loginProgressBar.progress = 25
                    }
                    1 -> {
                        loginProgressBar.progress = 50
                    }
                    2 -> {
                        loginProgressBar.progress = 75
                    }
                    3 -> {
                        loginProgressBar.progress = 100
                    }
                }*/
            }

            override fun onPageSelected(position: Int) {
                Toast.makeText(applicationContext,"$position",Toast.LENGTH_SHORT).show()
                var progress = 100 / fragList.size
                loginProgressBar.progress = (position + 1) * progress
                currentItem = position
                if (currentItem == fragList.size-1) {
                        nextButton.isActivated = false
                        buttonNEXT!!.text = "Finalizar"
                }else {
                    buttonNEXT!!.text = "Siguiente"
                }
            }

        })
    }

    private inner class ScreenSlidePageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            //currentItem = position
            return fragList[position]
        }
        override fun getCount(): Int = fragList.size

    }
}
