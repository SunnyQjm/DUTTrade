package com.j.ming.duttrade.extensions

import android.content.Intent
import android.support.v4.app.Fragment
import com.j.ming.duttrade.model.params.IntentParam


fun Fragment.jumpTo(cls: Class<*>, intentParam: IntentParam? = null, vararg flags: Int) {
    activity?.jumpTo(cls, intentParam, *flags)
}

fun Fragment.jumpForResult(cls: Class<*>, requestCode: Int, intentParam: IntentParam? = null) {
    val intent = Intent(activity, cls)
    intentParam?.applyParam(intent)
    startActivityForResult(intent, requestCode)
}