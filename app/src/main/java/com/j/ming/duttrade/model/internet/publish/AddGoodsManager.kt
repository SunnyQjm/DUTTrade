package com.j.ming.duttrade.model.internet.publish

import android.content.Context
import android.content.Intent

object AddGoodsManager {

    class AddGoodsParams(val name: String, val description: String, val pictures: Array<String>,
                         val price: Float, val discount: Float, val remainNum: Int, val qqNumber: String,
                         val phoneNumber: String, val wechatNumber: String)

    fun addGoods(context: Context, addGoodsParams: AddGoodsParams) {
        Intent(context, AddGoodsService::class.java).run {
            putExtra(AddGoodsService.PARAM_NAME, addGoodsParams.name)
            putExtra(AddGoodsService.PARAM_DESCRIPTION, addGoodsParams.description)
            putExtra(AddGoodsService.PARAM_PHONE_NUMBER, addGoodsParams.phoneNumber)
            putExtra(AddGoodsService.PARAM_WECHAT, addGoodsParams.wechatNumber)
            putExtra(AddGoodsService.PARAM_QQ, addGoodsParams.qqNumber)
            putExtra(AddGoodsService.PARAM_REMAIN_NUM, addGoodsParams.remainNum)
            putExtra(AddGoodsService.PARAM_DISCOUNT, addGoodsParams.discount)
            putExtra(AddGoodsService.PARAM_PRICE, addGoodsParams.price)
            putExtra(AddGoodsService.PARAM_PICTURES, addGoodsParams.pictures)
            context.startService(this)
        }
    }

}