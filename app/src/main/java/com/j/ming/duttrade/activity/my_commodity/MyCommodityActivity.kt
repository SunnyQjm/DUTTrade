package com.j.ming.duttrade.activity.my_commodity

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.j.ming.dcim.views.RecyclerViewDividerItem
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.activity.BaseRecyclerViewActivity
import com.j.ming.duttrade.activity.index.market.MarketFragment
import com.j.ming.duttrade.extensions.toast
import com.j.ming.duttrade.model.data.Commodity
import com.j.ming.duttrade.model.data.UserInfo
import com.j.ming.duttrade.model.event.RefreshEvent
import com.j.ming.easybar2.EasyBar
import com.j.ming.easybar2.init
import kotlinx.android.synthetic.main.activity_base_recycler_view.*
import kotlinx.android.synthetic.main.bar_item.*

class MyCommodityActivity : BaseRecyclerViewActivity<MyCommodityActivityPresenter>(),
        MyCommodityActivityContract.View{
    override fun loadData(isRefresh: Boolean) {
        val query = BmobQuery<Commodity>()
        query.addWhereEqualTo("owner", BmobUser.getCurrentUser(UserInfo::class.java))
        query.setLimit(MarketFragment.PAGE_SIZE)
        page = if (isRefresh)
            1
        else
            page + 1
        query.setSkip((page - 1) * MarketFragment.PAGE_SIZE)
        query.findObjects(object : FindListener<Commodity>() {
            override fun done(p0: MutableList<Commodity>?, p1: BmobException?) {
                adapter ?: return
                if (p1 == null)
                    p0?.let { commdities ->
                        if (commdities.size == 0) {
                            toast(R.string.all_data_load_finish)
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
                    toast(R.string.server_error)
                }
                refreshFinish(RefreshEvent())
            }
        })
    }

    override fun onCretePresenter(): MyCommodityActivityPresenter =
            MyCommodityActivityPresenter(this)

    override fun initView() {
        super.initView()
        easyBar.init(mode = EasyBar.Mode.ICON_, titleRes = R.string.my_commodity, leftCallback = {
            onBackPressed()
        })
        adapter = CommodityAdapter(mutableListOf())
        adapter?.run {
            recyclerView.addItemDecoration(RecyclerViewDividerItem(this@MyCommodityActivity))
            bindToRecyclerView(recyclerView)
        }

        initialLoadData()
    }
}
