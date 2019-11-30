package com.shibuyaxpress.petchaserkt.components.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.fragment.app.Fragment
import com.shibuyaxpress.petchaserkt.R
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener

class HomeFragment: Fragment() {

    private var carouselView: CarouselView? = null
    private var imageList = intArrayOf(R.drawable.car_img_1, R.drawable.car_img_2, R.drawable.car_img_3)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*return super.onCreateView(inflater, container, savedInstanceState)*/
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        setCarouselView(rootView)
        return rootView
    }

    private fun setCarouselView(view:View) {
        carouselView = view.findViewById(R.id.carouselView)
        carouselView!!.setImageListener(imageListener)
        carouselView!!.pageCount = imageList.size
        carouselView!!.setAnimateOnBoundary(true)
    }

    private var imageListener: ImageListener = ImageListener { position, imageView ->
        imageView.setImageResource(imageList[position])
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}