package com.j.ming.duttrade.views

import android.content.Context
import android.widget.ImageView
import com.j.ming.duttrade.extensions.load
import com.youth.banner.loader.ImageLoader

class GlideImageLoader: ImageLoader(){
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        path ?: return
        imageView?.load(path as String)
    }
}