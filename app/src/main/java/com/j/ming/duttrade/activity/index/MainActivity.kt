package com.j.ming.duttrade.activity.index

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.DUTTradeFragment
import com.j.ming.duttrade.activity.base.MVPBaseActivity
import com.j.ming.duttrade.activity.index.market.MarketFragment
import com.j.ming.duttrade.activity.index.mine.MineFragment
import com.j.ming.duttrade.extensions.scaleXY
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_bottom.*

class MainActivity : MVPBaseActivity<MainActivityPresenter>(), MainActivityContract.View, DUTTradeFragment.OnFragmentInteractionListener{
    override fun onFragmentInteraction(uri: Uri?) {

    }

    override fun onCretePresenter(): MainActivityPresenter =
            MainActivityPresenter(this)

    private val fragmentList = mutableListOf<Fragment>()
    private lateinit var mainAdapter: MainAdapter;

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
    }

    private fun select(position: Int) {
        when(position){
            MAIN_BOTTOM_MARKET -> {
                imgMarket.scaleXY(1f, 1.2f, 1f)
                imgMarket.updateDrawableTinColor(R.color.colorPrimary)
                imgMine.updateDrawableTinColor(R.color.grey)
                viewPager.currentItem = MAIN_BOTTOM_MARKET
            }
            MAIN_BOTTOM_MINE -> {
                imgMine.scaleXY(1f, 1.2f, 1f)
                imgMarket.updateDrawableTinColor(R.color.grey)
                imgMine.updateDrawableTinColor(R.color.colorPrimary)
                viewPager.currentItem = MAIN_BOTTOM_MINE
            }
        }
    }
}
