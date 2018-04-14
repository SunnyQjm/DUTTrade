package com.j.ming.duttrade.activity.register

import com.j.ming.duttrade.R
import com.j.ming.duttrade.model.data.UserInfo
import com.j.ming.duttrade.utils.AccountValidatorUtil

class RegisterActivityPresenter(registerActivity: RegisterActivity)
    : RegisterActivityContract.Presenter() {
    override fun registerFail() {
        mView.registerFail()
    }

    override fun sendVerificationCode(userName: String, registerType: RegisterActivityContract.RegisterType) {
        if (userName.isEmpty()) {
            toast(R.string.phone_number_not_allow_empty)
            registerFail()
            return
        }
        when (registerType) {
            RegisterActivityContract.RegisterType.PHONE -> {
                if (AccountValidatorUtil.isMobile(userName))
                    mModel.sendVerificationCodeToPhone(userName)
                else {
                    toast(R.string.please_input_correct_phone_numbet)
                    registerFail()
                }
            }
            RegisterActivityContract.RegisterType.NORMAL -> {
            }
        }

    }

    override fun sendVerificationCodeSuccess() {
        mView.sendVerificationCodeSuccess()
    }

    override fun registerSuccess(userInfo: UserInfo, registerType: RegisterActivityContract.RegisterType) {
        mView.registerSuccess(userInfo, registerType)
    }

    override fun registerByPhone(userName: String, password: String, verificationCode: String) {
        if(userName.isEmpty() || verificationCode.isEmpty() || password.isEmpty()){
            toast(R.string.please_complete_info)
            registerFail()
            return
        }
        mModel.registerByPhone(userName, password, verificationCode)
    }

    override fun register(username: String, password: String) {
        if(username.isEmpty() || password.isEmpty()) {
            toast(R.string.please_complete_info)
            registerFail()
            return
        }
        mModel.register(username, password)
    }

    init {
        mView = registerActivity
        mModel = RegisterActivityModel(this)
    }
}