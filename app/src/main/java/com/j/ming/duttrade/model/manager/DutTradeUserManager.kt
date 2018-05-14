package com.j.ming.duttrade.model.manager

import cn.bmob.v3.BmobUser

object DutTradeUserManager{
    fun isLogin() = BmobUser.getCurrentUser() != null
}