package com.j.ming.duttrade.model.data

import cn.bmob.v3.BmobUser

data class UserInfo(val descption: String = "", val avatar: String = "") : BmobUser() {
}