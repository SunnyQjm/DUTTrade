package com.j.ming.duttrade.activity.index.market

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.j.ming.dcim.GlideApp
import com.j.ming.duttrade.R
import com.j.ming.duttrade.model.data.Commodity

fun ImageView.loadCommodityPicture(url: String){
    GlideApp.with(context)
            .load(url)
            .error(R.drawable.none)
            .into(this)
}

class CommodityAdapter(mList: MutableList<Commodity>)
    : BaseQuickAdapter<Commodity, BaseViewHolder>(R.layout.commodity_item, mList) {
    override fun convert(helper: BaseViewHolder?, item: Commodity?) {
        helper ?: return
        item ?: return

        helper.setText(R.id.tvName, item.title)
                .getView<ImageView>(R.id.imgPicture)
                .loadCommodityPicture(item.pictures?.first()?.fileUrl ?: "")
    }

}