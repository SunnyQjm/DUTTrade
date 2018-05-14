package com.j.ming.duttrade.activity.register

import android.os.Bundle
import android.view.View
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.activity.MVPBaseActivity
import com.j.ming.duttrade.activity.index.MainActivity
import com.j.ming.duttrade.extensions.hideSoftKeyboard
import com.j.ming.duttrade.extensions.jumpTo
import com.j.ming.duttrade.extensions.scaleXY
import com.j.ming.duttrade.extensions.setStyleText
import com.j.ming.duttrade.model.data.UserInfo
import com.j.ming.duttrade.utils.doInterval
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.toolbar.*

class RegisterActivity : MVPBaseActivity<RegisterActivityPresenter>(),
        RegisterActivityContract.View {
    override fun registerFail() {
        changeRegisterState(false)
    }

    override fun registerSuccess(userInfo: UserInfo, registerType: RegisterActivityContract.RegisterType) {
        //注册成功
        jumpTo(MainActivity::class.java)
        finish()
    }

    override fun sendVerificationCodeSuccess() {
        tvClickSend.setTextColor(resources.getColor(R.color.grey))
        tvClickSend.isClickable = false
        doInterval(start = 1, count = 60) {
            if (it < 60L) {
                tvClickSend.text = String.format(getString(R.string.send_code_after_several_second), 60 - it)
            } else {
                tvClickSend.isClickable = true
                tvClickSend.setStyleText(textRes = R.string.click_send, colorRes = R.color.colorPrimary)
            }
        }
    }

    override fun onCretePresenter(): RegisterActivityPresenter =
            RegisterActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()
    }


    private var registerType = RegisterActivityContract.RegisterType.PHONE
    private fun initView() {
        setSupportActionBar(toolBar)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
            "".trimMargin()
        }

        /**
         * 默认用手机注册
         */
        radioGroup.check(R.id.checkPhoneVerification)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            registerType = when (checkedId) {
                R.id.checkNoneVerification -> {
                    toggleVerification(false)
                    RegisterActivityContract.RegisterType.NORMAL
                }
                else -> {
                    toggleVerification(true)
                    RegisterActivityContract.RegisterType.PHONE
                }
            }
        }
        tvClickSend.setOnClickListener {
            mPresenter.sendVerificationCode(etUsername.text.toString(), registerType)
        }

        btnRegister.setOnClickListener {
            hideSoftKeyboard(it)
            changeRegisterState(true)
            when (registerType) {
                RegisterActivityContract.RegisterType.NORMAL -> {
                    mPresenter.register(etUsername.text.toString(), etPwd.text.toString())
                }
                RegisterActivityContract.RegisterType.PHONE -> {
                    mPresenter.registerByPhone(etUsername.text.toString(), etPwd.text.toString(),
                            etVerificationCode.text.toString())
                }
            }
        }
        imageView.updateDrawableTinColor(R.color.colorPrimary)
    }

    private var isShowVerification = true
    private fun toggleVerification(change: Boolean) {
        if (change == isShowVerification)
            return
        isShowVerification = change
        if (!change) {
            etVerificationCode.scaleXY(1f, 0f)
            tvClickSend.scaleXY(1f, 0f)
        } else {
            etVerificationCode.scaleXY(0f, 1f)
            tvClickSend.scaleXY(0f, 1f)
        }
    }

    private var isRegistering = false
    private fun changeRegisterState(change: Boolean){
        if(change == isRegistering)
            return
        isRegistering = change
        if(change){
            btnRegister.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
        } else {
            btnRegister.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
        }
    }
}
