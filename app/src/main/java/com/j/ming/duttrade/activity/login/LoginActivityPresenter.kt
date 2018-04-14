package com.j.ming.duttrade.activity.login

import com.j.ming.duttrade.R

class LoginActivityPresenter(loginActivity: LoginActivity) : LoginActivityContract.Presenter() {
    override fun loginFail() {
        mView.loginFail()
    }

    override fun loginSuccess() {
        mView.loginSuccess()
    }

    override fun login(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            toast(R.string.please_complete_info)
            loginFail()
            return
        }
        mModel.login(username, password)
    }

    init {
        mView = loginActivity
        mModel = LoginActivityModel(this)
    }
}