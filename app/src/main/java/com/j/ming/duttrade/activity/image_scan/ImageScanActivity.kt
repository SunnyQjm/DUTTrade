package com.j.ming.duttrade.activity.image_scan

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.MVPBaseActivity
import com.sunny.scrollphotoview.ScrollPhotoView
import kotlinx.android.synthetic.main.activity_image_scan.*

class ImageScanActivity : MVPBaseActivity<ImageScanActivityPresenter>(), ImageScanActivityContract.View {
    companion object {
        const val PARAM_URLS = "PARAM_URLS"
        const val PARAM_POSITION = "PARAM_POSITION"
    }
    override fun onCretePresenter(): ImageScanActivityPresenter =
            ImageScanActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_scan)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            window.navigationBarColor = Color.TRANSPARENT
        }
        intent.getStringArrayExtra(PARAM_URLS)?.let {
            spv.setUrls(it)
        }
        intent.getIntExtra(PARAM_POSITION, 0).let {
            spv.setCurrentItem(it)
        }
        spv.imgLoader = { url, view->
            Glide.with(this)
                    .load(url)
                    .into(view)
        }

        spv.onScrollPhotoViewClickListener = object : ScrollPhotoView.OnScrollPhotoViewClickListener{
            override fun onDoubleTap(e: MotionEvent?) {
            }
            override fun onClick(e: MotionEvent?) {
                onBackPressed()
            }
        }
    }
}
