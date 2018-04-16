package com.j.ming.duttrade.activity.addgoods

import com.j.ming.duttrade.activity.base.mvp.BaseModel
import com.j.ming.duttrade.activity.base.mvp.BasePresenter
import com.j.ming.duttrade.activity.base.mvp.BaseView

interface AddGoodsActivityContract {
    interface View : BaseView {

    }

    interface Model : BaseModel {

    }

    abstract class Presenter : BasePresenter<View, Model>() {

    }
}