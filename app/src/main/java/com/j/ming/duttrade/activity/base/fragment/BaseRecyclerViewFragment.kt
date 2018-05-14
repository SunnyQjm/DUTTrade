package com.j.ming.duttrade.activity.base.fragment

import android.support.annotation.CallSuper
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.j.ming.arcmenu2.extension.MyDelegates
import com.j.ming.duttrade.R
import com.j.ming.duttrade.activity.base.mvp.BasePresenter
import com.j.ming.duttrade.model.event.LoadFinishEvent
import com.j.ming.duttrade.model.event.RefreshEvent
import com.j.ming.duttrade.views.easy_refresh.CustomLinerLayoutManager
import com.j.ming.duttrade.views.easy_refresh.EasyRefreshFooter
import com.j.ming.duttrade.views.easy_refresh.EasyRefreshLayout
import com.j.ming.duttrade.views.easy_refresh.arrow.ArrowRefreshHeader
import kotlinx.android.synthetic.main.base_recycler_view_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by sunny on 18-1-1.
 */

abstract class BaseRecyclerViewFragment<P : BasePresenter<*, *>> : MVPBaseFragment<P>(),
        EasyRefreshLayout.OnLoadListener, EasyRefreshLayout.OnRefreshListener {

    /**
     * 加载完成脚部布局
     */
    protected var endView: View? = null

    /**
     * 分页
     */
    protected var page = 1



    protected var adapter: BaseQuickAdapter<*, *>? = null
    protected var linearLayoutManager by MyDelegates.notNullAndOnlyInitFirstTime<CustomLinerLayoutManager>()

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


    override fun getRes() = R.layout.base_recycler_view_layout


    override fun initialLoadData() {
        loadData(true)
    }

    /**
     * 加载数据的回调，由子类调用
     * @param isRefresh true则标识是刷新数据，false则表示加载更多
     */
    protected abstract fun loadData(isRefresh: Boolean)

    @CallSuper
    override fun initView() {
        linearLayoutManager = CustomLinerLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
        //设置数据充满布局才允许上拉加载更多
        refreshLayout.setLoadOnlyDataFullScreen(true)
        //设置头布局
        val easyRefreshHeaderHandler = ArrowRefreshHeader(R.layout.easy_refresh_header)
        refreshLayout.setHeader(easyRefreshHeaderHandler)
        //设置尾布局
        val easyRefreshFooterHandler = EasyRefreshFooter(R.layout.easy_refresh_footer)
        refreshLayout.setFooter(easyRefreshFooterHandler)

        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnLoadListener(this)
    }


    override fun onRefresh() {
        //开始刷新时禁止滚动
        this.linearLayoutManager.setScrollAble(false)
        refreshLayout?.setLoadAble(true)
        loadData(true)
        onRefreshing()
    }

    override fun onLoad() {
        //开始加载更多时禁止滚动
        this.linearLayoutManager.setScrollAble(false)
        loadData(false)
        onLoading()
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

    protected open fun updateAll(){
        adapter?.notifyDataSetChanged()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshFinish(event: RefreshEvent) {
        if(!prepare)
            return
        progressBar?.post {
            if (progressBar.visibility == View.VISIBLE) {
                progressBar.visibility = View.INVISIBLE
                adapter?.setEmptyView(R.layout.recycler_empty_view)
            }
        }
        refreshLayout?.let {
            this.linearLayoutManager.setScrollAble(true)
            it.closeLoad()
            it.closeRefresh()
        }

        this.onFinish()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun loadFinish(event: LoadFinishEvent){
        refreshLayout?.setLoadAble(false)
    }


}