package com.j.ming.duttrade.views.easy_refresh.arrow

import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.j.ming.duttrade.R
import com.j.ming.duttrade.views.easy_refresh.EasyRefreshHeaderHandler

/**
 * Created by Sunny on 2017/9/10 0010.
 */

class ArrowRefreshHeader(headResourceId: Int, private val refreshingText: String = "正在刷新",
                         private val refreshPreText: String = "下拉刷新",
                         private val refreshHalfText: String = "释放刷新")
    : EasyRefreshHeaderHandler(headResourceId) {

    override fun getView(inflater: LayoutInflater, viewGroup: ViewGroup) {
        println("getView")
        inflater.inflate(headerResourceId, viewGroup, true)
    }

    override fun scrolling(header: View, scrollDistance: Int, totalHeaderHeight: Int) {
        if (scrollDistance > totalHeaderHeight * changeRate) {
            println("scrolling1")
            this.setText(header, R.id.refresh_text, refreshHalfText)
            this.setRotation(header, R.id.refresh_arrow, 180f)
            this.setVisibility(header, R.id.refresh_text, VISIBLE)
            this.setVisibility(header, R.id.refresh_progressBar, INVISIBLE)
            this.setVisibility(header, R.id.refresh_arrow, VISIBLE)
        } else {
            println("scrolling2")
            this.setText(header, R.id.refresh_text, refreshPreText)
            this.setRotation(header, R.id.refresh_arrow, 0f)
            this.setVisibility(header, R.id.refresh_text, VISIBLE)
            this.setVisibility(header, R.id.refresh_progressBar, INVISIBLE)
            this.setVisibility(header, R.id.refresh_arrow, VISIBLE)
        }
    }

    override fun init(header: View) {
        println("clear")
        this.setText(header, R.id.refresh_text, refreshPreText)
        this.setRotation(header, R.id.refresh_arrow, 0f)
        this.setVisibility(header, R.id.refresh_text, VISIBLE)
        this.setVisibility(header, R.id.refresh_progressBar, INVISIBLE)
        this.setVisibility(header, R.id.refresh_arrow, VISIBLE)
    }

    override fun refreshing(header: View) {
        println("refreshing")
        this.setText(header, R.id.refresh_text, refreshingText)
        this.setVisibility(header, R.id.refresh_text, VISIBLE)
        this.setVisibility(header, R.id.refresh_progressBar, VISIBLE)
        this.setVisibility(header, R.id.refresh_arrow, INVISIBLE)
    }

    override fun refreshFinish(header: View) {
        println("refresh finish")
        //        clear(header);
    }
}
