package com.j.ming.duttrade.activity.commodity_detail

import android.os.Bundle
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.activity.MVPBaseActivity

class CommodityDetailActivity : MVPBaseActivity<CommodityDetailActivityPresenter>(),
        CommodityDetailActivityContract.View{
    override fun onCretePresenter(): CommodityDetailActivityPresenter =
            CommodityDetailActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commodity_detail)
    }
}
