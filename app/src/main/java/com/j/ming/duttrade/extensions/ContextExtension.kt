package com.j.ming.duttrade.extensions

import android.content.Context
import android.content.Intent
import android.support.annotation.StringRes
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

fun Context.toast(info: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, info, duration)
            .show()
}

fun Context.toast(@StringRes stringRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, stringRes, duration)
            .show()
}


fun <T: Any> Context.jumpTo(clazz: Class<T>){
    startActivity(Intent(this, clazz))
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