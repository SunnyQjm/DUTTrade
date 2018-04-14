package com.j.ming.duttrade.activity.register

import cn.bmob.v3.BmobSMS
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.LogInListener
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.SaveListener
import com.j.ming.duttrade.model.data.UserInfo
import java.util.*

class RegisterActivityModel(val mPresenter: RegisterActivityPresenter) : RegisterActivityContract.Model {

    /**
     * 用户名密码注册
     */
    override fun register(username: String, password: String) {
        val user = UserInfo()
        user.username = username
        user.setPassword(password)
        user.signUp(object : SaveListener<UserInfo>() {
            override fun done(p0: UserInfo?, p1: BmobException?) {
                p1?.let {
                    mPresenter.registerFail()
                    p1.message?.let { mPresenter.toast(it) }
                }
                        ?: p0?.let { mPresenter.registerSuccess(it, RegisterActivityContract.RegisterType.NORMAL) }
                        ?: mPresenter.registerFail()
            }
        })
    }

    /**
     * 手机号验证码注册
     */
    override fun registerByPhone(username: String, password: String, verificationCode: String) {
        val user = UserInfo()
        user.username = username
        user.setPassword(password)
        BmobUser.signOrLoginByMobilePhone(username, verificationCode, object : LogInListener<UserInfo>() {
            override fun done(p0: UserInfo?, p1: BmobException?) {
                if (p1 != null || p0 == null) {
                    mPresenter.registerFail()
                    p1?.let { it.message?.let { mPresenter.toast(it) } }
                } else {
                    mPresenter.registerSuccess(p0, RegisterActivityContract.RegisterType.PHONE)
                }
            }
        })
    }

//    override fun sendVerificationCodeToEmail(email: String) {
//        BmobUser.requestEmailVerify(email, object : UpdateListener() {
//            override fun done(p0: BmobException?) {
//                p0?.let {
//                    it.message?.let { mPresenter.toast(it) }
//                } ?: mPresenter.sendVerificationCodeSuccess()
//            }
//        })
//    }

    override fun sendVerificationCodeToPhone(phoneNumber: String) {
        BmobSMS.requestSMS(phoneNumber, "normal", Date().toString(), object : QueryListener<Int>() {
            override fun done(p0: Int?, p1: BmobException?) {
                if (p1 == null)
                    mPresenter.sendVerificationCodeSuccess()
                else {
                    mPresenter.registerFail()
                    p1.message?.let {
                        mPresenter.toast(it)
                    }
                }
            }
        })
    }
}