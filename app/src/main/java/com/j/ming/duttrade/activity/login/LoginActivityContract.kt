package com.j.ming.duttrade.activity.login

import com.j.ming.duttrade.activity.base.mvp.BaseModel
import com.j.ming.duttrade.activity.base.mvp.BasePresenter
import com.j.ming.duttrade.activity.base.mvp.BaseView

interface LoginActivityContract {
    interface View : BaseView {
        fun loginSuccess()
        fun loginFail()
    }

    interface Model : BaseModel {
        fun login(username: String, password: String)
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun login(username: String, password: String)
        abstract fun loginSuccess()
        abstract fun loginFail()
    }
}