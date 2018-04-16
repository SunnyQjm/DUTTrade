package com.j.ming.duttrade.activity.index

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import cn.bmob.v3.BmobUser
import com.j.ming.arcmenu2.ArcMenu
import com.j.ming.arcmenu2.FloatingButton
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.addgoods.AddGoodsActivity
import com.j.ming.duttrade.activity.base.DUTTradeFragment
import com.j.ming.duttrade.activity.base.MVPBaseActivity
import com.j.ming.duttrade.activity.index.market.MarketFragment
import com.j.ming.duttrade.activity.index.mine.MineFragment
import com.j.ming.duttrade.extensions.jumpTo
import com.j.ming.duttrade.extensions.scaleXY
import com.j.ming.duttrade.extensions.toast
import com.j.ming.duttrade.utils.DensityUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_bottom.*

class MainActivity : MVPBaseActivity<MainActivityPresenter>(), MainActivityContract.View, DUTTradeFragment.OnFragmentInteractionListener{
    override fun onFragmentInteraction(uri: Uri?) {

    }

    override fun onCretePresenter(): MainActivityPresenter =
            MainActivityPresenter(this)

    private val fragmentList = mutableListOf<Fragment>()
    private lateinit var mainAdapter: MainAdapter;
    private lateinit var fab: ArcMenu
    companion object {
        const val MAIN_BOTTOM_MARKET = 0
        const val MAIN_BOTTOM_MINE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        fragmentList.add(MarketFragment.newInstance())
        fragmentList.add(MineFragment.newInstance())
        mainAdapter = MainAdapter(supportFragmentManager, fragmentList)
        viewPager.adapter = mainAdapter
        viewPager.currentItem = 0
        viewPager.offscreenPageLimit = 2
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                when(position){
                    MAIN_BOTTOM_MARKET -> {
                        fab.alpha = 1 - positionOffset
                    }
                }
            }

            override fun onPageSelected(position: Int) {
                select(position)
            }

        })
        imgMarket.updateDrawableTinColor(R.color.colorPrimary)
        imgMarket.setOnClickListener {
            select(MAIN_BOTTOM_MARKET)
        }
        imgMine.updateDrawableTinColor(R.color.grey)
        imgMine.setOnClickListener {
            select(MAIN_BOTTOM_MINE)
        }


        initFab()
    }

    private fun initFab() {
        ArcMenu
        fab = ArcMenu.Builder(this)
                //设置菜单按钮的大小
                .size(resources.getDimensionPixelSize(R.dimen.fab_menu_size))
                //设置菜单按钮的图标
                .contentRes(R.drawable.icon_add)
                //设置菜单的内边距
                .contentMargin(resources.getDimensionPixelSize(R.dimen.fab_menu_content_margin))
                .layoutMarginHorizon(resources.getDimensionPixelSize(R.dimen.fab_layout_horizon_margin))
                .layoutMarginVertical(resources.getDimensionPixelSize(R.dimen.fab_layout_vertical_margin))
                //设置菜单放置的位置
                .position(FloatingButton.Position.POSITION_BOTTOM_RIGHT)
                .bgRes(R.drawable.fab_bg)
                //设置展开动画的时长
                .duration(300)
                .build()
        fab.startAngle = -90
        fab.endAngle = 0

        fab.setOnClickListener {
            BmobUser.getCurrentUser()?.let {
                jumpTo(AddGoodsActivity::class.java)
            } ?: toast(R.string.please_login_first)
        }
//子菜单弧形展开的半径
        fab.radius = DensityUtil.dip2px(this, 100f)
        //四个参数分别是子菜单的大小，内边距，背景资源，和图标资源
//        fab.addItem(resources.getDimensionPixelSize(R.dimen.fab_menu_size),
//                resources.getDimensionPixelSize(R.dimen.fab_menu_content_margin),
//                contentRes = R.mipmap.ic_launcher)
//        fab.addItem(resources.getDimensionPixelSize(R.dimen.fab_menu_size),
//                resources.getDimensionPixelSize(R.dimen.fab_menu_content_margin),
//                contentRes = R.mipmap.ic_launcher)
//        fab.addItem(resources.getDimensionPixelSize(R.dimen.fab_menu_size),
//                resources.getDimensionPixelSize(R.dimen.fab_menu_content_margin),
//                contentRes = R.mipmap.ic_launcher)
    }

    private fun select(position: Int) {
        when(position){
            MAIN_BOTTOM_MARKET -> {
                imgMarket.scaleXY(1f, 1.2f, 1f)
                imgMarket.updateDrawableTinColor(R.color.colorPrimary)
                imgMine.updateDrawableTinColor(R.color.grey)
                viewPager.currentItem = MAIN_BOTTOM_MARKET
                fab.visibility = View.VISIBLE
            }
            MAIN_BOTTOM_MINE -> {
                imgMine.scaleXY(1f, 1.2f, 1f)
                imgMarket.updateDrawableTinColor(R.color.grey)
                imgMine.updateDrawableTinColor(R.color.colorPrimary)
                viewPager.currentItem = MAIN_BOTTOM_MINE
                fab.visibility = View.GONE
                fab.alpha = 1f
            }
        }
    }
}
