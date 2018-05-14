package com.j.ming.duttrade.activity.image_scan

import com.j.ming.duttrade.activity.base.mvp.BaseModel
import com.j.ming.duttrade.activity.base.mvp.BasePresenter
import com.j.ming.duttrade.activity.base.mvp.BaseView


interface ImageScanActivityContract {
    interface View : BaseView {

    }

    interface Model : BaseModel {

    }

    abstract class Presenter : BasePresenter<View, Model>() {

    }
}