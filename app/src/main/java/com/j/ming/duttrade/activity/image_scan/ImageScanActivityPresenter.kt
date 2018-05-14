package com.j.ming.duttrade.activity.image_scan


class ImageScanActivityPresenter(imageScanActivity: ImageScanActivity)
    : ImageScanActivityContract.Presenter(){
    init {
        mView = imageScanActivity
        mModel = ImageScanActivityModel(this)
    }
}