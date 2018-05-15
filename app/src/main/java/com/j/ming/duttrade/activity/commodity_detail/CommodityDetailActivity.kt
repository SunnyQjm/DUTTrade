package com.j.ming.duttrade.activity.commodity_detail

import android.content.Intent
import android.os.Bundle
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.activity.MVPBaseActivity
import com.j.ming.duttrade.activity.image_scan.ImageScanActivity
import com.j.ming.duttrade.extensions.jumpTo
import com.j.ming.duttrade.extensions.toast
import com.j.ming.duttrade.model.data.Commodity
import com.j.ming.duttrade.model.data.UserInfo
import com.j.ming.duttrade.model.params.IntentParam
import com.j.ming.duttrade.views.GlideImageLoader
import com.j.ming.easybar2.EasyBar
import com.j.ming.easybar2.init
import kotlinx.android.synthetic.main.activity_commodity_detail.*
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
        const val PARAM_POSITION = "PARAM_POSITION"

        const val RESULT_POSITION = "RESULT_POSITION"
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

    private var isOwner: Boolean = false
    private var commodity: Commodity? = null
    private var position: Int = -1

    private fun initView() {
        easyBar.init(mode = EasyBar.Mode.ICON_TEXT, titleRes = R.string.commodity_deatil,  leftCallback = {
            onBackPressed()
        }, rightText = "", rightCallback = {
            toast(R.string.edit)
        })
        intent.run {
            getStringArrayExtra(PARAM_URLS)
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
            position = getIntExtra(PARAM_POSITION, -1)
        }

        btnReserve.setOnClickListener {
            if(isOwner){        //执行删除操作
                this.commodity?.let {
                    Commodity().run {
                        objectId = it.objectId
                        delete(object : UpdateListener(){
                            override fun done(p0: BmobException?) {
                                if(p0 == null) {
                                    Intent().run {
                                        putExtra(RESULT_POSITION, position)
                                        setResult(0, this)
                                    }
                                    onBackPressed()
                                } else {
                                    toast(R.string.delete_commodity_fail)
                                    p0.printStackTrace()
                                }
                            }
                        })
                    }
                }
            } else {            //执行预定商品操作

            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onReceiveCommodityInfo(commodity: Commodity) {
        this.commodity = commodity
        tvName.text = commodity.title
        tvPrice.text = String.format("%s %.2f", "¥", commodity.originPrice)
        tvDescription.text = String.format("%s: %s", getString(R.string.description), commodity.description)
        tvQQ.text = String.format("%s: %s", "QQ", commodity.qqNumber)
        tvWechat.text = String.format("%s: %s", getString(R.string.wechat), commodity.weChatCount)
        tvPhone.text = String.format("%s: %s", getString(R.string.phone_number), commodity.phoneNumber)

        //如果是用户自己的商品，显示删除编辑
        if(commodity.owner.objectId == BmobUser.getCurrentUser().objectId){
            btnReserve.setText(R.string.delete)
            easyBar.setRightText(getString(R.string.edit))
            isOwner = true
        } else {
            isOwner = false
            btnReserve.setText(R.string.reserve)
        }
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
