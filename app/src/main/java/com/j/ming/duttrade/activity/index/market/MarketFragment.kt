package com.j.ming.duttrade.activity.index.market

import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.fragment.BaseRecyclerViewFragment
import com.j.ming.duttrade.activity.commodity_detail.CommodityDetailActivity
import com.j.ming.duttrade.extensions.jumpTo
import com.j.ming.duttrade.extensions.toast
import com.j.ming.duttrade.model.data.Commodity
import com.j.ming.duttrade.model.event.LoadFinishEvent
import com.j.ming.duttrade.model.event.RefreshEvent
import com.j.ming.duttrade.model.params.IntentParam
import com.j.ming.easybar2.EasyBar
import com.j.ming.easybar2.init
import kotlinx.android.synthetic.main.bar_item.*
import kotlinx.android.synthetic.main.base_recycler_view_layout.*
import org.greenrobot.eventbus.EventBus


class MarketFragment : BaseRecyclerViewFragment<MarketFragmentPresenter>(), MarketFragmentContract.View {
    override fun loadData(isRefresh: Boolean) {
        val query = BmobQuery<Commodity>()
        query.setLimit(PAGE_SIZE)
        page = if (isRefresh)
            1
        else
            page + 1
        query.setSkip((page - 1) * PAGE_SIZE)
        query.findObjects(object : FindListener<Commodity>() {
            override fun done(p0: MutableList<Commodity>?, p1: BmobException?) {
                adapter ?: return
                if (p1 == null)
                    p0?.let { commdities ->
                        if (commdities.size == 0) {
                            loadFinish(LoadFinishEvent())
                            context?.toast(R.string.all_data_load_finish)
                        }
                        adapter?.let {
                            if (isRefresh) {
                                it.data.clear()
                                it.notifyDataSetChanged()
                            }
                            (it as CommodityAdapter).addData(commdities)
                        }
                    }
                else {
                    context?.toast(R.string.server_error)
                }
                refreshFinish(RefreshEvent())
            }
        })
    }

    companion object {
        const val PAGE_SIZE = 10
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
        super.initView()
        easyBar.init(titleRes = R.string.trade_center, mode = EasyBar.Mode.NONE)
        adapter = CommodityAdapter(mutableListOf())
        adapter?.run {
            recyclerView.setHasFixedSize(false)
            val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            layoutManager.gapStrategy + StaggeredGridLayoutManager.GAP_HANDLING_NONE
            recyclerView.layoutManager = layoutManager
            bindToRecyclerView(recyclerView)
            setOnItemClickListener { adapter, view, position ->
                jumpTo(CommodityDetailActivity::class.java, IntentParam()
                        .add(CommodityDetailActivity.PARAM_URLS, (this as CommodityAdapter).getItem(position)
                                ?.pictures?.map { it.fileUrl }?.toTypedArray()))
                val commodity = (this as CommodityAdapter).getItem(position)
                EventBus.getDefault()
                        .postSticky(commodity)
            }
        }

        initialLoadData()
    }
}