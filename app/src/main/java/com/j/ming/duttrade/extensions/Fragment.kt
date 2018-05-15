package com.j.ming.duttrade.extensions

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import com.j.ming.duttrade.activity.commodity_detail.CommodityDetailActivity
import com.j.ming.duttrade.activity.index.market.MarketFragment
import com.j.ming.duttrade.model.data.Commodity
import com.j.ming.duttrade.model.params.IntentParam
import org.greenrobot.eventbus.EventBus


fun Fragment.jumpTo(cls: Class<*>, intentParam: IntentParam? = null, vararg flags: Int) {
    activity?.jumpTo(cls, intentParam, *flags)
}

fun Fragment.jumpForResult(cls: Class<*>, requestCode: Int, intentParam: IntentParam? = null) {
    val intent = Intent(activity, cls)
    intentParam?.applyParam(intent)
    startActivityForResult(intent, requestCode)
}


fun Fragment.jumpToCommodityDetail(commodity: Commodity, position: Int){
    jumpForResult(CommodityDetailActivity::class.java, MarketFragment.REQUEST_CODE_DETAIL, IntentParam()
            .add(CommodityDetailActivity.PARAM_URLS, commodity.pictures?.map { it.fileUrl }?.toTypedArray())
            .add(CommodityDetailActivity.PARAM_POSITION, position)
    )
    EventBus.getDefault()
            .postSticky(commodity)
}

fun Activity.jumpToCommodityDetail(commodity: Commodity, position: Int){
    jumpForResult(CommodityDetailActivity::class.java, MarketFragment.REQUEST_CODE_DETAIL, IntentParam()
            .add(CommodityDetailActivity.PARAM_URLS, commodity.pictures?.map { it.fileUrl }?.toTypedArray())
            .add(CommodityDetailActivity.PARAM_POSITION, position)
    )
    EventBus.getDefault()
            .postSticky(commodity)
}