package com.j.ming.duttrade.model.internet.publish

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UploadBatchListener
import com.j.ming.duttrade.R
import com.j.ming.duttrade.extensions.toast
import com.j.ming.duttrade.model.data.Commodity
import com.j.ming.duttrade.model.data.UserInfo
import com.j.ming.duttrade.utils.notification.MyNotificationUtil
import com.j.ming.duttrade.utils.notification.PublishNotificationFactoryImpl
import com.j.ming.duttrade.utils.notification.PublishNotificationInfo
import rx.Observable
import rx.schedulers.Schedulers
import top.zibin.luban.Luban
import java.io.File
import java.util.*

class AddGoodsService : Service() {
    companion object {
        const val PARAM_NAME = "PARAM_NAME"
        const val PARAM_DESCRIPTION = "PARAM_DESCRIPTION"
        const val PARAM_PICTURES = "PARAM_PICTURES"
        const val PARAM_PRICE = "PARAM_PRICE"
        const val PARAM_DISCOUNT = "PARAM_DISCOUNT"
        const val PARAM_REMAIN_NUM = "PARAM_REMAIN_NUM"
        const val PARAM_QQ = "PARAM_QQ"
        const val PARAM_WECHAT = "PARAM_WECHAT"
        const val PARAM_PHONE_NUMBER = "PARAM_PHONE_NUMBER"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.run {
            val name = getStringExtra(PARAM_NAME)
            val description = getStringExtra(PARAM_DESCRIPTION)
            val pictures = getStringArrayExtra(PARAM_PICTURES)
            val price = getFloatExtra(PARAM_PRICE, 0f)
            val discount = getFloatExtra(PARAM_DISCOUNT, 0f)
            val remainNum = getIntExtra(PARAM_REMAIN_NUM, 0)
            val qqNumber = getStringExtra(PARAM_QQ)
            val wechatNumber = getStringExtra(PARAM_WECHAT)
            val phoneNumber = getStringExtra(PARAM_PHONE_NUMBER)

            val notifyId = System.currentTimeMillis().toInt()
            Observable.just(Arrays.asList(*pictures))
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map<MutableList<File>> {
                        Log.e("TAG", "do map")
                        try {
                            //首先对要上传的图片进行压缩
                            Luban.with(this@AddGoodsService)
                                    .setTargetDir("")
                                    .ignoreBy(200)
                                    .load(it)
                                    .get()
                        } catch (e: Exception) {
                            e.printStackTrace()
                            null
                        }
                    }.subscribe({
                        Log.e("TAG", "onNext")
                        BmobFile.uploadBatch(it.map { it.path }.toTypedArray(), object : UploadBatchListener {
                            override fun onSuccess(files: MutableList<BmobFile>?, urls: MutableList<String>?) {
                                urls ?: return
                                files ?: return
                                println("---------上传成功---------")
                                //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                                //2、urls-上传文件的完整url地址
                                if (urls.size == it.size) { //如果数量相等，则代表文件全部上传完成
                                    //上传完成后关掉通知
                                    MyNotificationUtil.newInstance(this@AddGoodsService)
                                            .cancel(notifyId)
                                    //do something
                                    Commodity(name, description, files.toTypedArray(), price, remainNum,
                                            discount, qqNumber, phoneNumber, wechatNumber,
                                            BmobUser.getCurrentUser(UserInfo::class.java))
                                            .save(object : SaveListener<String>() {
                                                override fun done(p0: String?, p1: BmobException?) {
                                                    if (p1 == null)
                                                        this@AddGoodsService.toast(R.string.already_publish)
                                                    else
                                                        p1.printStackTrace()
                                                }
                                            })
                                }
                            }

                            //1、curIndex--表示当前第几个文件正在上传
                            //2、curPercent--表示当前上传文件的进度值（百分比）
                            //3、total--表示总的上传文件数
                            //4、totalPercent--表示总的上传进度（百分比）
                            override fun onProgress(curIndex: Int, curPercent: Int, total: Int, totalPercent: Int) {
                                PublishNotificationFactoryImpl()
                                        .build(this@AddGoodsService, PublishNotificationInfo(curIndex, curPercent, total, totalPercent))
                                        ?.let {
                                            MyNotificationUtil.newInstance(this@AddGoodsService)
                                                    .notify(it, notifyId)
                                        }
                            }

                            override fun onError(p0: Int, p1: String?) {
                                println("----------------上传出错--------------")
                                println(p1 ?: "")
                            }

                        })
                    }, {
                        it.printStackTrace()
                    }, {
                        Log.e("TAG", "complete")
                    })
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}