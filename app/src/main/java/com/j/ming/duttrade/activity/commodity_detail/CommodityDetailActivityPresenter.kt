package com.j.ming.duttrade.activity.commodity_detail


class CommodityDetailActivityPresenter(commodityDetailActivity: CommodityDetailActivity)
    : CommodityDetailActivityContract.Presenter(){
    init {
        mView = commodityDetailActivity
        mModel = CommodityDetailActivityModel(this)
    }
}