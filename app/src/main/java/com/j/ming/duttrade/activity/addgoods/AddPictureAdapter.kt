package com.j.ming.duttrade.activity.addgoods

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.j.ming.duttrade.R

data class ImageItem(val path: String = "", val type: Type = Type.TYPE_PICTURE) {
    enum class Type {
        TYPE_ADD_TAG, TYPE_PICTURE
    }
}

class AddPictureAdapter(images: MutableList<ImageItem>)
    : BaseQuickAdapter<ImageItem, BaseViewHolder>(R.layout.add_picture_item, images) {

    override fun convert(helper: BaseViewHolder?, item: ImageItem?) {
        helper ?: return
        item ?: return

        val imageView = helper.getView<ImageView>(R.id.imageView)
        when(item.type){
            ImageItem.Type.TYPE_PICTURE -> {
                Glide.with(mContext)
                        .load(item.path)
                        .into(imageView)
            }
            ImageItem.Type.TYPE_ADD_TAG -> {
                Glide.with(mContext)
                        .load(R.drawable.icon_addpicture)
                        .into(imageView)
            }
        }
    }
}