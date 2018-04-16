package com.j.ming.duttrade.activity.index.mine

import android.os.Bundle
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.MVPBaseFragment

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

    override fun getRes(): Int = R.layout.fragment_mine

    override fun initView() {
    }

    override fun initialLoadData() {
    }

}