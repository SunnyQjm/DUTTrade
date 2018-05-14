package com.j.ming.duttrade.activity.index.mine

import android.os.Bundle
import cn.bmob.v3.BmobUser
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.fragment.MVPBaseFragment
import com.j.ming.duttrade.activity.login.LoginActivity
import com.j.ming.duttrade.extensions.jumpTo
import com.j.ming.easybar2.EasyBar
import com.j.ming.easybar2.init
import kotlinx.android.synthetic.main.bar_item.*
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment: MVPBaseFragment<MineFragmentPresenter>(), MineFragmentContract.View{

    companion object {
        fun newInstance(): MineFragment {
            val args = Bundle()
            val fragment = MineFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreatePresenter(): MineFragmentPresenter =
            MineFragmentPresenter(this)

    override fun onResume() {
        super.onResume()
        initialLoadData()
    }
    override fun getRes(): Int = R.layout.fragment_mine

    override fun initView() {
        easyBar.init(titleRes = R.string.person_center, mode = EasyBar.Mode.NONE)
        tvExist.setOnClickListener {
            BmobUser.logOut()
            jumpTo(LoginActivity::class.java)
        }

        cl_avatar.setOnClickListener {
            if(BmobUser.getCurrentUser() == null){
                jumpTo(LoginActivity::class.java)
            }
        }

    }

    override fun initialLoadData() {
        BmobUser.getCurrentUser()?.let {
            tvName.text = it.username
        } ?: tvName.setText(R.string.click_me_login)
    }

}