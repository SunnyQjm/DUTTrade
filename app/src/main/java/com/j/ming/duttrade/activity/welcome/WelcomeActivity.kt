package com.j.ming.duttrade.activity.welcome

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.MVPBaseActivity
import com.j.ming.duttrade.activity.index.MainActivity
import com.j.ming.duttrade.extensions.jumpTo
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : MVPBaseActivity<WelcomeActivityPresenter>(),
        WelcomeActivityContract.View{
    override fun onCretePresenter(): WelcomeActivityPresenter =
            WelcomeActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        if (!this.isTaskRoot) {
            val mainIntent = intent
            val action = mainIntent.action
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER)
                    && action != null && action == Intent.ACTION_MAIN) {
                finish()
                return
            }
        }

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

        imageView.updateDrawableTinColor(R.color.colorPrimary)
        welcome_layout.postDelayed({
            jumpTo(MainActivity::class.java)

//            if(BmobUser.getCurrentUser() == null){      //没有登录则跳到登录界面
//                jumpTo(LoginActivity::class.java)
//            } else {
//                Log.d(TAG(), "isMyBean: ${BmobUser.getCurrentUser() is UserInfo}")
//                jumpTo(MainActivity::class.java)
//            }
            finish()
        }, 1000)
    }
}
