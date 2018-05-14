package com.j.ming.duttrade.utils.notification

//1、curIndex--表示当前第几个文件正在上传
//2、curPercent--表示当前上传文件的进度值（百分比）
//3、total--表示总的上传文件数
//4、totalPercent--表示总的上传进度（百分比）
class PublishNotificationInfo(val curIndex: Int = 0, val curPercent: Int = 0,
                              val total: Int = 0, val totalPercent: Int = 0, val indeterminate: Boolean = false)