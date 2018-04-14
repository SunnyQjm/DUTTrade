package com.j.ming.duttrade

import android.app.Application
import cn.bmob.v3.Bmob

class App: Application(){
    override fun onCreate() {
        super.onCreate()
        //第一：默认初始化
        Bmob.initialize(this, "7c0fd6ca835f65a01edf3697d88fd055")
    }
}