package com.j.ming.duttrade.activity.index.market

class MarketFragmentPresenter(marketFragment: MarketFragment) : MarketFragmentContract.Presenter(){
    init {
        mView = marketFragment
        mModel = MarketFragmentModel(this)
    }
}