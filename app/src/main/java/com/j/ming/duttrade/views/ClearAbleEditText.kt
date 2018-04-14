package com.j.ming.duttrade.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent

class ClearAbleEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                                  defStyleAttr: Int = android.R.attr.editTextStyle)
    : AppCompatEditText(context, attrs, defStyleAttr) {

    private val rightDrawable: Drawable? = compoundDrawables[2]

    init {
        rightDrawable?.setBounds(0, 0,
                (rightDrawable.intrinsicWidth * 0.65).toInt(),
                (rightDrawable.intrinsicHeight * 0.65).toInt()
        )

        changeDrawables(right = null)
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length ?: 0 > 0) {
                    changeDrawables(right = rightDrawable)
                } else {
                    changeDrawables(right = null)
                }
            }

        })
        setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus || text.isEmpty())
                changeDrawables(right = null)
            else
                changeDrawables(right = rightDrawable)
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP) {
            //getTotalPaddingRight()图标左边缘至控件右边缘的距离
            //getWidth() - getTotalPaddingRight()表示从最左边到图标左边缘的位置
            //getWidth() - getPaddingRight()表示最左边到图标右边缘的位置
            val touchable = event.x > width - totalPaddingRight && event.x < width - paddingRight

            if (touchable) {
                this.setText("")
            }
        }
        return super.onTouchEvent(event)
    }

    private fun changeDrawables(left: Drawable? = compoundDrawables[0], top: Drawable? = compoundDrawables[1], right: Drawable? = compoundDrawables[2],
                                bottom: Drawable? = compoundDrawables[3]) {
        setCompoundDrawables(left, top, right, bottom)
    }
}

