package com.shibuyaxpress.petchaserkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_register.*

private const val NUM_PAGES = 5

class RegisterActivity : AppCompatActivity() {

    private lateinit var mPager: ViewPager
    private var currentItem = 0
    private var fragList = ArrayList<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //set fragments for list frag
        fragList.add(RegisterNameFragment())
        fragList.add(RegisterEmailFragment())
        fragList.add(RegisterAddressFragment())
        fragList.add(RegisterContactPhoneFragment())
        //initiate a ViewPager and a PageAdapter
        mPager = findViewById(R.id.pager)

        //the page adapter, who provides the pages to the view adapter widget
        val pageAdapter = ScreenSlidePageAdapter(supportFragmentManager)
        mPager.adapter = pageAdapter

        nextButton.setOnClickListener {
            Toast.makeText(this@RegisterActivity, "$currentItem", Toast.LENGTH_SHORT).show()
            if (currentItem < fragList.size) {
                currentItem+=1
            mPager.setCurrentItem(currentItem, true)
            } else {
                nextButton.isActivated = false
            }
        }
    }

    private inner class ScreenSlidePageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            //currentItem = position
            return fragList[position]
        }
        override fun getCount(): Int = fragList.size

    }
}
