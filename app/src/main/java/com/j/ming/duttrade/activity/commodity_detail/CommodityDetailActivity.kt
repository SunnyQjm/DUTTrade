package com.j.ming.duttrade.activity.commodity_detail

import android.os.Bundle
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.activity.MVPBaseActivity
import com.j.ming.duttrade.activity.image_scan.ImageScanActivity
import com.j.ming.duttrade.extensions.jumpTo
import com.j.ming.duttrade.model.data.Commodity
import com.j.ming.duttrade.model.data.UserInfo
import com.j.ming.duttrade.model.params.IntentParam
import com.j.ming.duttrade.views.GlideImageLoader
import com.j.ming.easybar2.EasyBar
import com.j.ming.easybar2.init
import kotlinx.android.synthetic.main.bar_item.*
import kotlinx.android.synthetic.main.commodity_detail_header_commodity_info.*
import kotlinx.android.synthetic.main.commodity_detail_header_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class CommodityDetailActivity : MVPBaseActivity<CommodityDetailActivityPresenter>(),
        CommodityDetailActivityContract.View {
    companion object {
        const val PARAM_URLS = "PARAM_URLS"
    }

    override fun onCretePresenter(): CommodityDetailActivityPresenter =
            CommodityDetailActivityPresenter(this)

    //如果你需要考虑更好的体验，可以这么操作
    override fun onStart() {
        super.onStart()
        EventBus.getDefault()
                .register(this)
        //开始轮播
        banner?.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault()
                .unregister(this)
        //结束轮播
        banner?.stopAutoPlay()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commodity_detail)
        initView()
    }

    private fun initView() {
        easyBar.init(mode = EasyBar.Mode.ICON_, titleRes = R.string.commodity_deatil, leftCallback = {
            onBackPressed()
        })
        intent.getStringArrayExtra(PARAM_URLS)
                ?.let {
                    banner.setOnBannerListener { position->
                        jumpTo(ImageScanActivity::class.java, IntentParam()
                                .add(ImageScanActivity.PARAM_URLS, it)
                                .add(ImageScanActivity.PARAM_POSITION, position)
                        )
                    }
                    banner.setImageLoader(GlideImageLoader())
                            .setImages(it.toMutableList())
                            .start()

                }

    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onReceiveCommodityInfo(commodity: Commodity) {
        tvName.text = commodity.title
        tvPrice.text = String.format("%s %.2f", "¥", commodity.originPrice)
        tvRemainNum.text = String.format("%s: %d", getString(R.string.stock), commodity.remainNum)
        tvDescription.text = String.format("%s: %s", getString(R.string.description), commodity.description)

        //查询一下用户信息
        BmobQuery<UserInfo>()
                .getObject(commodity.owner.objectId, object : QueryListener<UserInfo>() {
                    override fun done(p0: UserInfo?, p1: BmobException?) {
                        p0?.let {
                            tvUser.text = String.format("%s: %s", getString(R.string.master), it.username)
                        }
                    }
                })
    }

}
