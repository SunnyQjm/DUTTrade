package com.j.ming.duttrade.activity.addgoods

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.j.ming.duttrade.R
import com.j.ming.duttrade.extensions.load

data class ImageItem(val path: String = "", val type: Type = Type.TYPE_PICTURE) {

    override fun hashCode(): Int {
        return path.hashCode() + type.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is ImageItem)
            return false
        return path == other.path && type == other.type
    }

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
        when (item.type) {
            ImageItem.Type.TYPE_PICTURE -> {
                imageView.load(item.path)
                helper.setVisible(R.id.imgDelete, true)
                helper.addOnClickListener(R.id.imgDelete)
            }
            ImageItem.Type.TYPE_ADD_TAG -> {
                imageView.setImageResource(R.drawable.icon_addpicture)
                helper.setVisible(R.id.imgDelete, false)
            }
        }
    }
}