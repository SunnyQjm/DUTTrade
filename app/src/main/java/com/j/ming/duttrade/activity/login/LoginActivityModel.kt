package com.j.ming.duttrade.activity.login

import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.LogInListener
import com.j.ming.duttrade.model.data.UserInfo
import java.util.*

class LoginActivityModel(private val mPresenter: LoginActivityPresenter) : LoginActivityContract.Model {
    override fun login(username: String, password: String) {
        BmobUser.loginByAccount(username, password, object : LogInListener<UserInfo>() {
            override fun done(p0: UserInfo?, p1: BmobException?) {
                p1?.let {
                    mPresenter.loginFail()
                    it.message?.let { mPresenter.toast(it) }
                } ?: p0?.let { mPresenter.loginSuccess() }
                ?: mPresenter.loginFail()
            }
        })
    }
}