package com.j.ming.duttrade.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.annotation.StringRes
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.j.ming.duttrade.model.params.IntentParam

fun Context.toast(info: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, info, duration)
            .show()
}

fun Context.toast(@StringRes stringRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, stringRes, duration)
            .show()
}


fun Context.jumpTo(cls: Class<*>, intentParam: IntentParam? = null, vararg flags: Int) {
    val intent = Intent(this, cls)
    flags.forEach {
        intent.addFlags(it)
    }
    intentParam?.applyParam(intent)
    startActivity(intent)
}

fun Activity.jumpForResult(cls: Class<*>, requestCode: Int, intentParam: IntentParam? = null) {
    val intent = Intent(this, cls)
    intentParam?.applyParam(intent)
    startActivityForResult(intent, requestCode)
}

/////////////////////////////////////////////////////////
///////// 下面是软键盘相关的扩展
////////////////////////////////////////////////////////

/**
 * 切换状态，打开则关闭，关闭则打开
 */
fun Context.changeSoftKeyboard() {
    val im = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    if (!im.isActive)
        return
    im.toggleSoftInput(0, 0)
}

/**
 * 隐藏软键盘
 */
fun Context.hideSoftKeyboard(view: View) {
    val im = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (!im.isActive)
        return
    //隐藏软键盘 //
    im.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showSoftKeyboard(view: View) {
    val im = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (im.isActive)
        return
    im.showSoftInputFromInputMethod(view.windowToken, 0)
}