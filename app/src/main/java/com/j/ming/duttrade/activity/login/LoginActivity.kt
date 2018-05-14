package com.j.ming.duttrade.activity.login

import android.os.Bundle
import android.view.View
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.activity.MVPBaseActivity
import com.j.ming.duttrade.activity.index.MainActivity
import com.j.ming.duttrade.activity.register.RegisterActivity
import com.j.ming.duttrade.extensions.hideSoftKeyboard
import com.j.ming.duttrade.extensions.jumpTo
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*

class LoginActivity : MVPBaseActivity<LoginActivityPresenter>(),
        LoginActivityContract.View{
    override fun loginFail() {
        changeLoginState(false)
    }

    /**
     * 登录成功
     */
    override fun loginSuccess() {
        jumpTo(MainActivity::class.java)
        finish()
    }

    override fun onCretePresenter(): LoginActivityPresenter =
            LoginActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {
        setSupportActionBar(toolBar)
        imageView.updateDrawableTinColor(R.color.colorPrimary)
        toolBar.setTitle(R.string.login)
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        tvRegister.setOnClickListener {
            jumpTo(RegisterActivity::class.java)
        }
        btnLogin.setOnClickListener {
            hideSoftKeyboard(it)
            changeLoginState(true)
            mPresenter.login(etUsername.text.toString(), etPwd.text.toString())
        }
    }

    private var isLogining = false

    private fun changeLoginState(change: Boolean){
        if(change == isLogining)
            return
        isLogining = change
        if(change){
            btnLogin.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
        } else {
            btnLogin.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
        }
    }
}
