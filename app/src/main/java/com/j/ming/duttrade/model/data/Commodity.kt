package com.j.ming.duttrade.model.data

import cn.bmob.v3.BmobObject
import cn.bmob.v3.datatype.BmobFile

/**
 * 商品信息Model
 *
 * @param name              商品的名字
 * @param description       对商品的描述
 * @param pictures          一个文件数组，包含了该商品的图片
 * @param originPrice       原价
 * @param remainNum         库存（扩展字段，未使用）
 * @param discount          折扣（扩展字段，未使用）
 * @param qqNumber          卖家的QQ号
 * @param weChatCount       卖家的微信号
 * @param owner             卖家的信息（与UserInfo是一对一的关联）
 * @see UserInfo
 */
class Commodity(val name: String = "",
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