package com.j.ming.duttrade.views

import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet

class TinImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                             defStyleAttr: Int = 0)
    : AppCompatImageView(context, attrs, defStyleAttr){

    private var mDrawable = drawable

    fun updateDrawableTinColor(@ColorRes color: Int){
        mDrawable = DrawableCompat.wrap(mDrawable.mutate())
        DrawableCompat.setTintList(mDrawable, ContextCompat.getColorStateList(context, color))
        setImageDrawable(mDrawable)
    }
}