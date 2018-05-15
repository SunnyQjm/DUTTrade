package com.j.ming.duttrade.activity.my_commodity

import com.j.ming.duttrade.activity.base.mvp.BaseModel
import com.j.ming.duttrade.activity.base.mvp.BasePresenter
import com.j.ming.duttrade.activity.base.mvp.BaseView


interface MyCommodityActivityContract {
    interface View : BaseView {

    }

    interface Model : BaseModel {

    }

    abstract class Presenter : BasePresenter<View, Model>() {

    }
}