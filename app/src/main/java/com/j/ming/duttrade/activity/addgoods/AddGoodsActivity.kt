package com.j.ming.duttrade.activity.addgoods

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.InputType
import com.j.ming.dcim.extensions.jumpTo
import com.j.ming.dcim.index.DCIMActivity
import com.j.ming.dcim.manager.EasyDCIM
import com.j.ming.dcim.manager.SelectPictureManager
import com.j.ming.dcim.model.params.EasyBarParams
import com.j.ming.dcim.model.params.IntentParam
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.activity.MVPBaseActivity
import com.j.ming.duttrade.activity.image_scan.ImageScanActivity
import com.j.ming.duttrade.extensions.hideSoftKeyboard
import com.j.ming.duttrade.extensions.showSoftKeyboard
import com.j.ming.duttrade.extensions.toast
import com.j.ming.duttrade.model.internet.publish.AddGoodsManager
import com.j.ming.duttrade.utils.AccountValidatorUtil
import com.j.ming.duttrade.views.EasyEditDialog
import com.j.ming.easybar2.EasyBar
import com.j.ming.easybar2.init
import kotlinx.android.synthetic.main.activity_add_goods.*
import kotlinx.android.synthetic.main.bar_item.*

class AddGoodsActivity : MVPBaseActivity<AddGoodsActivityPresenter>(), AddGoodsActivityContract.View {
    override fun onCretePresenter(): AddGoodsActivityPresenter =
            AddGoodsActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_goods)
        initView()
    }

    private lateinit var addPictureAdapter: AddPictureAdapter
    private fun initView() {
        //清空以前的选中状态
        SelectPictureManager.clear()

        easyBar.init(titleRes = R.string.add_goods, rightRes = R.drawable.icon_sure,
                leftRes = R.drawable.back, mode = EasyBar.Mode.ICON, leftCallback = {
            onBackPressed()
        }, rightCallback = {
            //添加商品
            addGoods()
        })

        // init recyclerView
        addPictureRecyclerView.layoutManager = GridLayoutManager(this, 3)
        addPictureAdapter = AddPictureAdapter(mutableListOf())
        addPictureAdapter.addData(ImageItem(type = ImageItem.Type.TYPE_ADD_TAG))
        addPictureAdapter.bindToRecyclerView(addPictureRecyclerView)
        addPictureAdapter.setOnItemClickListener { adapter, view, position ->
            adapter as AddPictureAdapter
            if (position == adapter.data.size - 1) {      //最后一个是添加图片的按钮
                EasyDCIM.with(this)
                        .setSaveState(true)
                        .setMode(EasyDCIM.MODE_SELECT_MULTI)
                        .setEasyBarParam(EasyBarParams(barBgColor = R.color.colorPrimary))
                        .jumpForResult(0
                        )
            } else {
                jumpTo(ImageScanActivity::class.java, IntentParam()
                        .add(ImageScanActivity.PARAM_URLS, adapter.data.map { imageItem ->
                            imageItem.path
                        }.toTypedArray())
                        .add(ImageScanActivity.PARAM_POSITION, position))
            }
        }

        addPictureAdapter.setOnItemChildClickListener { adapter, view, position ->
            //移除已选中的图片
            adapter as AddPictureAdapter
            when (view.id) {
                R.id.imgDelete -> {
                    adapter.getItem(position)?.let {
                        SelectPictureManager.remove(it.path)
                    }
                    adapter.remove(position)
                }
            }
        }



        addGoodsPrice.setOnClickListener {
            EasyEditDialog(this, rightCallback = {
                addGoodsPrice.setValue(it)
            })
                    .inputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
                    .title(R.string.please_input_price)
                    .value(addGoodsPrice.value())
                    .show()
            showSoftKeyboard(addGoodsPrice)
        }

        addGoodsRemain.setOnClickListener {
            EasyEditDialog(this, rightCallback = {
                addGoodsRemain.setValue(it)
            }).inputType(InputType.TYPE_CLASS_NUMBER)
                    .title(R.string.please_input_remain_num)
                    .value(addGoodsRemain.value())
                    .show()
        }

        addGoodsPhone.setOnClickListener {
            EasyEditDialog(this, rightCallback = {
                if (AccountValidatorUtil.isMobile(it))
                    addGoodsPhone.setValue(it)
                else
                    toast(R.string.please_input_correct_phone_numbet)
            }).inputType(InputType.TYPE_CLASS_TEXT)
                    .title(R.string.please_input_phone_number)
                    .value(addGoodsPhone.value())
                    .show()
        }

        addGoodsQQ.setOnClickListener {
            EasyEditDialog(this, rightCallback = {
                addGoodsQQ.setValue(it)
            }).inputType(InputType.TYPE_CLASS_TEXT)
                    .title(R.string.please_input_qq_number)
                    .value(addGoodsQQ.value())
                    .show()
        }

        addGoodsWeChat.setOnClickListener {
            EasyEditDialog(this, rightCallback = {
                addGoodsWeChat.setValue(it)
            }).inputType(InputType.TYPE_CLASS_TEXT)
                    .title(R.string.please_input_wechat_count)
                    .value(addGoodsWeChat.value())
                    .show()
        }
    }

    /**
     * 执行添加商品操作
     */
    private fun addGoods() {
        if (etName.text.toString() == "" || etDescription.text.toString() == "")
            toast(R.string.please_complete_info)
        else {
            AddGoodsManager.addGoods(this, AddGoodsManager.AddGoodsParams(
                    name = etName.text.toString(),
                    description = etDescription.text.toString(),
                    pictures = SelectPictureManager.selectedPath(),
                    price = addGoodsPrice.value().toFloat(),
                    discount = 1f,
                    remainNum = addGoodsRemain.value().toInt(),
                    qqNumber = addGoodsQQ.value(),
                    phoneNumber = addGoodsPhone.value(),
                    wechatNumber = addGoodsWeChat.value()
            ))
            onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0 -> {  //选择图片回调
                data?.getStringArrayExtra(DCIMActivity.RESULT_PATHS)?.forEach {
                    if (addPictureAdapter.data.indexOf(ImageItem(it)) < 0)      //原来不存在则添加
                        addPictureAdapter.addData(0, ImageItem(it))
                }
                addPictureAdapter.notifyDataSetChanged()
            }

        }
    }

    override fun onBackPressed() {
        hideSoftKeyboard(etDescription)
        super.onBackPressed()
    }
}
