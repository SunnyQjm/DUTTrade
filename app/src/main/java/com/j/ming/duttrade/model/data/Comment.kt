package com.j.ming.duttrade.model.data

import cn.bmob.v3.BmobObject

/**
 * 评论
 */
class Comment(
        val content: String,
        val commentUser: UserInfo,
        val commodity: Commodity
): BmobObject()