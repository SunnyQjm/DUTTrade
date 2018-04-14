package com.j.ming.duttrade.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet
import android.view.View
import com.j.ming.duttrade.R


open class TinEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                            defStyleAttr: Int = android.R.attr.editTextStyle)
    : AppCompatEditText(context, attrs, defStyleAttr), View.OnFocusChangeListener {
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if(hasFocus){
            updateDrawablesTinColor(R.color.colorPrimary)
        } else {
            updateDrawablesTinColor(R.color.grey)
        }
    }

    companion object {
        const val LEFT = 0x0001
        const val TOP = 0x0002
        const val RIGHT = 0x0004
        const val BOTTOM = 0x0008
        const val ALL = 0x000f
    }

    var tints: Int = LEFT
    protected lateinit var mDrawables: Array<Drawable?>
    init {
        if (!isInEditMode) {
            mDrawables = compoundDrawables
            loadFromAttribute(context, attrs, defStyleAttr)
            this.onFocusChangeListener = this
        }
    }

    private fun loadFromAttribute(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.TinEditText)
            tints = ta.getInt(R.styleable.TinEditText_tints, LEFT)
            ta.recycle()
        }
        onFocusChange(this, isFocused)
    }

    private fun updateDrawablesTinColor(@ColorRes colorPrimary: Int) {
        //设置icon
        if(mDrawables[0] != null && (tints and LEFT) == 1){
            mDrawables[0] = DrawableCompat.wrap(mDrawables[0]!!.mutate())
            DrawableCompat.setTintList(mDrawables[0]!!, ContextCompat.getColorStateList(context, colorPrimary))
        }

        if(mDrawables[1] != null && (tints and TOP) == 1){
            mDrawables[1] = DrawableCompat.wrap(mDrawables[1]!!.mutate())
            DrawableCompat.setTintList(mDrawables[1]!!, ContextCompat.getColorStateList(context, colorPrimary))
        }

        if(mDrawables[2] != null && (tints and RIGHT) == 1){
            mDrawables[2] = DrawableCompat.wrap(mDrawables[2]!!.mutate())
            DrawableCompat.setTintList(mDrawables[2]!!, ContextCompat.getColorStateList(context, colorPrimary))
        }

        if(mDrawables[3] != null && (tints and BOTTOM) == 1){
            mDrawables[3] = DrawableCompat.wrap(mDrawables[3]!!.mutate())
            DrawableCompat.setTintList(mDrawables[3]!!, ContextCompat.getColorStateList(context, colorPrimary))
        }
        setCompoundDrawablesWithIntrinsicBounds(mDrawables[0], mDrawables[1], mDrawables[2], mDrawables[3])
    }
}