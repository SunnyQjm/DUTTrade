package com.j.ming.duttrade.model.data

import cn.bmob.v3.BmobObject
import cn.bmob.v3.datatype.BmobFile

/**
 * 商品类
 */
class Commodity(val title: String = "",
                val description: String = "",
                val pictures: Array<BmobFile>? = null,
                val originPrice: Float = 1f,
                val remainNum: Int = 1,             //库存
                val discount: Float = 1f,
                val qqNumber: String = "",
                val phoneNumber: String = "",
                val weChatCount: String = "",
                val owner: UserInfo = UserInfo()
                ) : BmobObject()