package com.j.ming.duttrade.activity.addgoods

class AddGoodsActivityPresenter(addGoodsActivity: AddGoodsActivity) : AddGoodsActivityContract.Presenter(){
    init {
        mView = addGoodsActivity
        mModel = AddGoodsActivityModel(this)
    }
}