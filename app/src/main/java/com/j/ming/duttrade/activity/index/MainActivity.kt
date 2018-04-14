package com.j.ming.duttrade.activity.index

import android.os.Bundle
import cn.bmob.v3.BmobUser
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.MVPBaseActivity
import com.j.ming.duttrade.activity.login.LoginActivity
import com.j.ming.duttrade.extensions.jumpTo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MVPBaseActivity<MainActivityPresenter>(), MainActivityContract.View {
    override fun onCretePresenter(): MainActivityPresenter =
            MainActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        textView.setOnClickListener {
            BmobUser.logOut()
            jumpTo(LoginActivity::class.java)
        }
    }
}
