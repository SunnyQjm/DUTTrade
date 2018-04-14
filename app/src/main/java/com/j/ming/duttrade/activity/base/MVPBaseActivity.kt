package com.j.ming.duttrade.activity.base

import android.os.Bundle
import android.support.annotation.CallSuper
import com.j.ming.duttrade.activity.base.mvp.BasePresenter

abstract class MVPBaseActivity<P : BasePresenter<*, *>> : DUTTradeActivity() {
    protected lateinit var mPresenter: P
    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = onCretePresenter()
    }

    abstract fun onCretePresenter(): P
}