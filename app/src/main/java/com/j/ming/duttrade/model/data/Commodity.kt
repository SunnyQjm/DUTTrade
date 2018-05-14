package com.j.ming.duttrade.model.data

import cn.bmob.v3.BmobObject
import cn.bmob.v3.datatype.BmobFile

/**
 * 商品类
 */
class Commodity(val title: String,
                val description: String,
                val pictures: Array<BmobFile>?,
                val originPrice: Float,
                val remainNum: Int,             //库存
                val discount: Float,
                val qqNumber: String,
                val phoneNumber: String,
                val weChatCount: String,
                val owner: UserInfo
                ) : BmobObject()