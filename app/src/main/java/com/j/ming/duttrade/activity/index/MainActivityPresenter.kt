package com.j.ming.duttrade.activity.index

class MainActivityPresenter(mainActivity: MainActivity) : MainActivityContract.Presenter(){
    init {
        mView = mainActivity
        mModel = MainActivityModel(this)
    }
}