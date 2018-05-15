package com.j.ming.duttrade.activity.my_commodity

class MyCommodityActivityPresenter(myCommodityActivity: MyCommodityActivity)
    : MyCommodityActivityContract.Presenter(){
    init {
        mView = myCommodityActivity
        mModel = MyCommodityActivityModel(this)
    }
}