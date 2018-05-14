package com.j.ming.duttrade.activity.addgoods

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.j.ming.dcim.index.DCIMActivity
import com.j.ming.dcim.manager.EasyDCIM
import com.j.ming.dcim.manager.SelectPictureManager
import com.j.ming.dcim.model.params.EasyBarParams
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.MVPBaseActivity
import com.j.ming.duttrade.extensions.hideSoftKeyboard
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
        })

        // init recyclerView
        addPictureRecyclerView.layoutManager = GridLayoutManager(this, 3)
        addPictureAdapter = AddPictureAdapter(mutableListOf())
        addPictureAdapter.addData(ImageItem(type = ImageItem.Type.TYPE_ADD_TAG))
        addPictureAdapter.bindToRecyclerView(addPictureRecyclerView)
        addPictureAdapter.setOnItemClickListener { adapter, view, position ->
            if (position == adapter.data.size - 1) {      //最后一个是添加图片的按钮
                EasyDCIM.with(this)
                        .setSaveState(true)
                        .setMode(EasyDCIM.MODE_SELECT_MULTI)
                        .setEasyBarParam(EasyBarParams(barBgColor = R.color.colorPrimary))
                        .jumpForResult(0
                        )
            }
        }

        addPictureAdapter.setOnItemChildClickListener { adapter, view, position ->      //移除已选中的图片
            adapter as AddPictureAdapter
            when(view.id){
                R.id.imgDelete -> {
                    adapter.getItem(position)?.let {
                        SelectPictureManager.remove(it.path)
                    }
                    adapter.remove(position)
                }
            }
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
        hideSoftKeyboard(et_content)
        super.onBackPressed()
    }
}
