package com.j.ming.duttrade.activity.my_commodity

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.j.ming.duttrade.R
import com.j.ming.duttrade.extensions.load
import com.j.ming.duttrade.model.data.Commodity
import com.j.ming.duttrade.utils.TimeUtils


class CommodityAdapter(mList: MutableList<Commodity>)
    : BaseQuickAdapter<Commodity, BaseViewHolder>(R.layout.commodity_item_more, mList) {
    override fun convert(helper: BaseViewHolder?, item: Commodity?) {
        helper ?: return
        item ?: return

        helper.setText(R.id.tvName, item.title)
                .setText(R.id.tvName, item.description)
                .setText(R.id.tvTime, TimeUtils.returnTime(TimeUtils.returnTimeStamps_DateTiem(item.createdAt)))
                .getView<ImageView>(R.id.imgPicture)
                .load(item.pictures?.first()?.fileUrl ?: "")
    }

}