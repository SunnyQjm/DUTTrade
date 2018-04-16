package com.j.ming.duttrade.activity.index.market

import android.os.Bundle
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.MVPBaseFragment
import com.j.ming.easybar2.EasyBar
import com.j.ming.easybar2.init
import kotlinx.android.synthetic.main.bar_item.*

class MarketFragment : MVPBaseFragment<MarketFragmentPresenter>(), MarketFragmentContract.View {

    companion object {
        fun newInstance(): MarketFragment {
            val args = Bundle()
            val fragment = MarketFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreatePresenter(): MarketFragmentPresenter =
            MarketFragmentPresenter(this)

    override fun getRes(): Int = R.layout.fragment_market

    override fun initView() {
        easyBar.init(titleRes = R.string.trade_center, mode = EasyBar.Mode.NONE)
    }

    override fun initialLoadData() {
    }

}