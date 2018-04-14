package com.j.ming.duttrade.activity.register

import com.j.ming.duttrade.activity.base.mvp.BaseModel
import com.j.ming.duttrade.activity.base.mvp.BasePresenter
import com.j.ming.duttrade.activity.base.mvp.BaseView
import com.j.ming.duttrade.model.data.UserInfo

class RegisterActivityContract {

    enum class RegisterType{
        PHONE, NORMAL
    }

    interface View : BaseView {
        fun registerSuccess(userInfo: UserInfo, registerType: RegisterType)
        fun registerFail()
        fun sendVerificationCodeSuccess()
    }

    interface Model : BaseModel {
        fun registerByPhone(username: String, password: String, verificationCode: String)
        fun register(username: String, password: String)
        fun sendVerificationCodeToPhone(phoneNumber: String)
//        fun sendVerificationCodeToEmail(email: String)
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun registerSuccess(userInfo: UserInfo, registerType: RegisterType)
        abstract fun registerFail()
        abstract fun registerByPhone(userName: String, password: String, verificationCode: String)
        abstract fun register(username: String, password: String)
        abstract fun sendVerificationCode(userName: String, registerType: RegisterType)
        abstract fun sendVerificationCodeSuccess()
    }
}