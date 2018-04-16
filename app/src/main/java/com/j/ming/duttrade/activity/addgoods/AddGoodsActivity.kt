package com.j.ming.duttrade.activity.addgoods

import android.os.Bundle
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.MVPBaseActivity
import com.j.ming.easybar2.EasyBar
import com.j.ming.easybar2.init
import kotlinx.android.synthetic.main.bar_item.*

class AddGoodsActivity : MVPBaseActivity<AddGoodsActivityPresenter>(), AddGoodsActivityContract.View {
    override fun onCretePresenter(): AddGoodsActivityPresenter =
            AddGoodsActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_goods)
        initView()
    }

    private fun initView() {
        easyBar.init(titleRes = R.string.add_goods, rightRes = R.drawable.icon_sure,
                leftRes = R.drawable.back, mode = EasyBar.Mode.ICON, leftCallback = {
            onBackPressed()
        })
    }
}
