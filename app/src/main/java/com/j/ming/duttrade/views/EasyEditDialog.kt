package com.j.ming.duttrade.views


import android.app.Dialog
import android.content.Context
import android.support.annotation.StringRes
import android.view.Gravity
import android.view.LayoutInflater
import com.j.ming.duttrade.R
import com.j.ming.duttrade.utils.DensityUtil
import kotlinx.android.synthetic.main.dialog_bottom_btn.*
import kotlinx.android.synthetic.main.youyun_edit_dialog.*


/**
 * Created by Sunny on 2017/8/19 0019.
 */

class EasyEditDialog(context: Context,
                     @StringRes private val leftTextRes: Int = R.string.cancel,
                     @StringRes private val rightTextRes: Int = R.string.sure,
                     private var widthPercent: Float = 0.7f,
                     val leftCallback: () -> Unit = {},
                     val rightCallback: (result: String) -> Unit = {})
    : Dialog(context, R.style.CustomDialog) {

    init {
        val mView = LayoutInflater.from(context)
                .inflate(R.layout.youyun_edit_dialog, null, false)
        mView.post {
            tvCancel.setText(leftTextRes)
            tvSure.setText(rightTextRes)
            tvCancel.setOnClickListener {
                dismiss()
                leftCallback()
            }

            tvSure.setOnClickListener {
                dismiss()
                rightCallback(editText.text.toString())
            }
        }
        super.setContentView(mView)
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
    }

    fun title(title: String): EasyEditDialog {
        tvTitle.text = title
        return this
    }

    fun title(@StringRes titleRes: Int): EasyEditDialog {
        tvTitle.setText(titleRes)
        return this
    }

    fun hint(hint: String): EasyEditDialog {
        editText.hint = hint
        return this
    }

    fun hint(@StringRes hintRes: Int): EasyEditDialog {
        editText.setHint(hintRes)
        return this
    }

    fun value(value: String): EasyEditDialog{
        editText.setText(value)
        return this
    }

    fun value(@StringRes value: Int): EasyEditDialog{
        editText.setText(value)
        return this
    }

    fun inputType(inputType: Int): EasyEditDialog {
        editText.inputType = inputType
        return this
    }

    override fun show() {
        editText.requestFocus()
        window?.let {
            val lp = it.attributes
            it.setGravity(Gravity.CENTER)
            lp.width = (DensityUtil.getScreenWidth(context) * widthPercent).toInt()
            it.attributes = lp
        }
        super.show()
    }
}
