package com.j.ming.duttrade.activity.base.activity

import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.j.ming.arcmenu2.extension.MyDelegates
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.mvp.BasePresenter
import com.j.ming.duttrade.activity.base.mvp.BaseView
import com.j.ming.duttrade.model.event.RefreshEvent
import com.j.ming.duttrade.views.easy_refresh.CustomLinerLayoutManager
import com.j.ming.duttrade.views.easy_refresh.EasyRefreshFooter
import com.j.ming.duttrade.views.easy_refresh.EasyRefreshLayout
import com.j.ming.duttrade.views.easy_refresh.arrow.ArrowRefreshHeader
import kotlinx.android.synthetic.main.activity_base_recycler_view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by sunny on 18-1-31.
 */

abstract class BaseRecyclerViewActivity<P : BasePresenter<*, *>> : MVPBaseActivity<P>(), BaseView,
        EasyRefreshLayout.OnRefreshListener, EasyRefreshLayout.OnLoadListener {


    protected var adapter: BaseQuickAdapter<*, *>? = null

    protected var linearLayoutManager by MyDelegates.notNullAndOnlyInitFirstTime<CustomLinerLayoutManager>()

    /**
     * 分页
     */
    protected var page = 1

    /**
     * 子类可以通过复写该函数来覆盖父类中的布局
     * 但是一定要包括父类布局中的元素
     */
    protected open fun getRes() = R.layout.activity_base_recycler_view

    override fun onStart() {
        super.onStart()
        EventBus.getDefault()
                .register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault()
                .unregister(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getRes())
        initView()
    }

    @CallSuper
    protected open fun initView() {
        linearLayoutManager = CustomLinerLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        //设置数据充满布局才允许上拉加载更多
        refreshLayout.setLoadOnlyDataFullScreen(true)
        //设置头布局
        val easyRefreshHeaderHandler = ArrowRefreshHeader(R.layout.easy_refresh_header,
                refreshingText = getString(getRefreshingTextRes()),
                refreshPreText = getString(getRefreshPreTextRes()),
                refreshHalfText = getString(getRefreshHalfTextRes()))
        refreshLayout.setHeader(easyRefreshHeaderHandler)
        //设置尾布局
        val easyRefreshFooterHandler = EasyRefreshFooter(R.layout.easy_refresh_footer)
        refreshLayout.setFooter(easyRefreshFooterHandler)

        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnLoadListener(this)
    }

    /**
     * 正在刷新
     */
    open fun getRefreshingTextRes() = R.string.refreshing

    /**
     * 下拉刷新
     */
    open fun getRefreshPreTextRes() = R.string.refresh_pre

    /**
     * 释放刷新
     */
    open fun getRefreshHalfTextRes() = R.string.refresh_half

    override fun onLoad() {
        //开始加载更多时禁止滚动
        this.linearLayoutManager.setScrollAble(false)
        loadData(false)
        onLoading()
    }

    override fun onRefresh() {
        //开始刷新时禁止滚动
        this.linearLayoutManager.setScrollAble(false)
        loadData(true)
        onRefreshing()
    }

    /**
     * 正在加载更多的回调
     */
    protected open fun onLoading() {}

    /**
     * 正在刷新的回调
     */
    protected open fun onRefreshing() {}

    /**
     * 刷新或加载更多完毕回调
     */
    protected open fun onFinish() {}

    protected open fun updateAll() {
        adapter?.notifyDataSetChanged()
    }

    protected open fun update(position: Int) {
        adapter?.notifyItemChanged(position)
    }

    protected open fun initialLoadData() {
        loadData(true)
    }

    /**
     * 加载数据的回调，由子类调用
     * @param isRefresh true则标识是刷新数据，false则表示加载更多
     */
    protected abstract fun loadData(isRefresh: Boolean)

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshFinish(event: RefreshEvent) {
        if (progressBar.visibility == View.VISIBLE) {
            progressBar.visibility = View.INVISIBLE
            adapter?.setEmptyView(R.layout.recycler_empty_view)
        }
        this.linearLayoutManager.setScrollAble(true)
        refreshLayout.closeLoad()
        refreshLayout.closeRefresh()
        this.onFinish()
    }
}