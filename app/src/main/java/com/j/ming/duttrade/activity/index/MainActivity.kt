package com.j.ming.duttrade.activity.index

import android.os.Bundle
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.MVPBaseActivity

class MainActivity : MVPBaseActivity<MainActivityPresenter>(), MainActivityContract.View {
    override fun onCretePresenter(): MainActivityPresenter =
            MainActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
    }
}
