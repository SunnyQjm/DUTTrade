package com.j.ming.duttrade.extensions

import android.widget.ImageView
import com.j.ming.dcim.GlideApp
import com.j.ming.duttrade.R


fun ImageView.load(url: String){
    GlideApp.with(context)
            .load(url)
            .centerCrop()
            .into(this)
}


