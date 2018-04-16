package com.j.ming.duttrade.extensions

import android.content.Intent
import android.support.v4.app.Fragment

fun <T: Any> Fragment.jumpTo(clazz: Class<T>){
    context?.startActivity(Intent(context, clazz))
}
