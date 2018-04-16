package com.j.ming.duttrade.activity.base

import android.os.Bundle
import com.j.ming.duttrade.activity.base.mvp.BasePresenter

abstract class MVPBaseFragment<P: BasePresenter<*, *>>: DUTTradeFragment(){

    protected lateinit var mPresenter: P
    abstract fun onCreatePresenter(): P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = onCreatePresenter()
    }
}