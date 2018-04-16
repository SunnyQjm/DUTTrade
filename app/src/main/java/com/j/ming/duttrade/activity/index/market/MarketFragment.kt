package com.j.ming.duttrade.activity.index.market

import android.os.Bundle
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.MVPBaseFragment

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
    }

    override fun initialLoadData() {
    }

}