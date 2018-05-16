package com.j.ming.duttrade.model.data

import cn.bmob.v3.BmobUser

/**
 * 用户信息Model
 *
 * @param descption 用户个签
 * @param avatar 用户头像
 */
data class UserInfo(val descption: String = "",
                    val avatar: String = "") : BmobUser() {
}