package com.j.ming.duttrade.model.data

import cn.bmob.v3.BmobObject

/**
 * 订单
 */
class Order(val commodity: Commodity,           //商品信息
            val owner: UserInfo,                //商品主人信息
            val reserver: UserInfo,             //预定卖家信息
            val state: Int)                     //当前订单的状态：0 -> 预定，待处理   1 -> 订单完成
                                                // 2 -> 主人取消  3 -> 买家取消
    : BmobObject(){}