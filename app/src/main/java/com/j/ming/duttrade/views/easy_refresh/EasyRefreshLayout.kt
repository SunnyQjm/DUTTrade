package com.j.ming.duttrade.views.easy_refresh

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v4.view.MotionEventCompat
import android.util.AttributeSet
import android.view.*
import android.widget.OverScroller
import com.j.ming.duttrade.extensions.doAnim
import com.j.ming.duttrade.views.easy_refresh.arrow.ArrowRefreshHeader

/**
 * Created by Sunny on 2017/9/6 0006.
 */

open class EasyRefreshLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ViewGroup(context, attrs, defStyleAttr) {

    /**
     * 用于完成滚动操作
     */
    private val mScroller: OverScroller

    /**
     * 判定为滚动的最小距离
     */
    private val mTouchSlop: Int

    /**
     * 手机按下时的屏幕坐标
     */
    private var mYDown: Float = 0.toFloat()

    /**
     * 手机当时所处的屏幕坐标
     */
    private var mYMove: Float = 0.toFloat()

    /**
     * 上次触发ACTION_MOVE事件时的屏幕坐标
     */
    private var mYLastMove: Float = 0.toFloat()

    /**
     * 移动比率，比率越高，下拉相对距离越小，如果等于1，则手指下拉多少距离，就实际下拉多少距离
     */
    private var moveRate = 1.5f


    @LayoutRes
    private val headerResourceId = -1

    @LayoutRes
    private val footerResourceId = -1

    private var header: View? = null
    private var easyRefreshHeader: EasyRefreshHeaderHandler? = null
    private var footer: View? = null
    private var easyRefreshFooter: EasyRefreshFooterHandler? = null
    private var contentView: View? = null
    private val inflater: LayoutInflater
    private var refreshListener: OnRefreshListener? = null
    private var loadListener: OnLoadListener? = null

    /**
     * 是否允许下拉或上滑
     */
    private var enable = true

    /**
     * 是否允许下拉刷新
     * 如果enable = false，则该参数失效
     */
    private var isRefreshAble = true

    /**
     * 内部条件，同上
     */
    private var innerIsRefreshAble = true

    /**
     * 是否允许上拉加载更多
     * 如果enable = false，则该参数失效
     */
    private var isLoadAble = true

    /**
     * 内部条件，同上
     */
    private var innerIsLoadAble = true

    /**
     * 是否当数据填充慢屏幕才允许上拉加载更多，默认是
     */
    private var loadOnlyDataFullScreen = true

    /**
     * 头布局的上边界，用于回弹
     */
    private var headerHeight: Int = 0

    /**
     * 本布局所占高度
     */
    private var layoutHeight: Int = 0

    /**
     * 内容布局的高度
     */
    private var contentViewHeight: Int = 0

    /**
     * 尾布局的高度
     */
    private var footerHeight: Int = 0

    /**
     * 当前状态
     */
    private var status = Status.NORMAL

    /**
     * 使用模式，默认是开启下拉刷新和上拉加载更多
     */
    private var mode = Mode.BOTH

    /**
     * 箭头转向比例，如果等于1则表示要头布局完全下拉箭头才转向
     */
    private var changeRate = 0.8f

    /**
     * 判断是不是没有更多数据可加载了
     * @return
     */
    /**
     * 设置所有数据都加载完毕
     * @param allDataLoadFinish
     */
    var isAllDataLoadFinish = false

    /**
     * 头部布局显示的模式
     * OVERLAP 重叠模式
     *
     * LINEAR  线性模式
     */
    var displayMode = DisplayMode.LINEAR

    /**
     * 处理多点触控的情况，准确地计算Y坐标和移动距离dy
     * 同时兼容单点触控的情况
     */
    private var mActivePointerId = MotionEvent.INVALID_POINTER_ID

    /**
     * 阻尼值，可以使得下拉越来越困难
     */
    private var damping = 1.0

    companion object {
        private const val DAMPING_SPAN = 0.99
    }

    private//如果当前在顶部，而且是下滑动作
    val isMoveAble: Boolean
        get() {
            if (contentView == null)
                return false
            if (enable && isRefreshAble && innerIsRefreshAble && isChildScrollToTop && mYMove - mYDown > 0) {
                return true
            }

            return (enable && (!loadOnlyDataFullScreen || contentViewHeight >= layoutHeight)
                    && isLoadAble && innerIsLoadAble && isChildScrollToBottom && mYMove - mYDown < 0)
        }

    /**
     * 判断contentView是否已经滑到顶部
     *
     * @return
     */
    private//如果contentView无法再继续向上滑则表示已经滑到顶部
    val isChildScrollToTop: Boolean
        get() = contentView != null && !contentView!!.canScrollVertically(-1)

    /**
     * 判断contentView是否已经滑到底部
     *
     * @return
     */
    private val isChildScrollToBottom: Boolean
        get() = contentView != null && !contentView!!.canScrollVertically(1)

    enum class Mode {
        REFRESH_ONLY, LOAD_ONLY, BOTH, NONE
    }

    enum class DisplayMode {
        OVERLAP, LINEAR
    }

    internal enum class Status {
        NORMAL, SCROLL_DOWN, SCROLL_DOWN_EFFECT, REFRESHING,
        SCROLL_UP, LOADING
    }


    init {
        initAttr(context, attrs, defStyleAttr)

        //获取inflater，用来加载头布局
        inflater = LayoutInflater.from(context)

        mScroller = OverScroller(context)
        //获取touchSlop
        val configuration = ViewConfiguration.get(context)
        mTouchSlop = configuration.scaledTouchSlop
    }

    /**
     * 初始化自定义参数
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private fun initAttr(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        //TODO clear param
    }

    /**
     * 从XML文件中加载一个布局后，当所有View均add完毕，会调用该方法
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        contentView = getChildAt(0)
        contentView!!.setPadding(0, 0, 0, 0)

        //如果id有效
        if (headerResourceId > 0) {
            //将头布局加载到当前布局
            inflater.inflate(headerResourceId, this, true)
            header = getChildAt(childCount - 1)
            easyRefreshHeader = ArrowRefreshHeader(headerResourceId)
        }

        if (footerResourceId > 0) {
            //添加加载布局
            inflater.inflate(footerResourceId, this, true)
            footer = getChildAt(childCount - 1)
            easyRefreshFooter = EasyRefreshFooter(footerResourceId)
            footer!!.visibility = View.INVISIBLE
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val childCount = childCount
        (0 until childCount)
                .map {
                    getChildAt(it)
                    //为每一个子控件计算size
                }
                .forEach { measureChild(it, widthMeasureSpec, heightMeasureSpec) }
    }

    /**
     * 部署内容视图和头部脚部的相对位置
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        contentView?.let {
            if (header != null) {
                headerHeight = header!!.measuredHeight
                when (displayMode) {
                    DisplayMode.LINEAR -> {
                        //将头部署在当前视图之外
                        header!!.layout(0, -headerHeight, width, 0)
                    }
                    DisplayMode.OVERLAP -> {
                        //将头部布局部署于内容布局之下
                        header!!.layout(0, 0, width, headerHeight)
                    }
                }
            } else {
                //如果头布局都没有，就不允许下拉刷新
                setRefreshAble(false)
            }

            //部署尾布局
            if (footer != null) {
                footer!!.layout(0, it.measuredHeight, width,
                        it.measuredHeight + footer!!.measuredHeight)
                footerHeight = footer!!.measuredHeight
            } else {
                //如果没有尾布局，就不允许上拉加载更多
                setLoadAble(false)
            }

            //部署内容
            it.layout(0, 0, width, it.measuredHeight)
            contentViewHeight = it.measuredHeight
            //获取当前布局的高度
            layoutHeight = height
        }
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    /**
     * 如果返回true，则会拦截下触摸事件，不向下传递，由本ViewGroup消费
     * 如果返回false或者执行super，则不拦截，向下传递
     *
     * @param ev
     * @return
     */

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        //处理多点触控个，精确计算手指移动的距离
        dealMulTouchEvent(ev)
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
                val diff = Math.abs(mYMove - mYDown)
                mYLastMove = mYMove

                //当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
                //这样就不会响应子控件的点击或者长按事件
                if (isMoveAble && diff > mTouchSlop && mode != Mode.NONE) {
                    if (mYMove - mYDown > 0 && isRefreshAble &&
                            (mode == Mode.BOTH || mode == Mode.REFRESH_ONLY)) {
                        if (easyRefreshHeader != null)
                            easyRefreshHeader!!.init(header)
                        status = Status.SCROLL_DOWN
                    } else if (isLoadAble && (mode == Mode.BOTH || mode == Mode.LOAD_ONLY)) {
                        if (easyRefreshFooter != null)
                            easyRefreshFooter!!.init(footer)
                        status = Status.SCROLL_UP
                        //上拉加载更多的时候显示底部布局
                        footer!!.visibility = View.VISIBLE
                    }
                    println("isRefreshable: " + isRefreshAble)
                    println("isLoadable: " + isLoadAble)
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        dealMulTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {

                //自按下滚动的距离
                val scrollDistance = Math.abs(scrollY)
                val deltaY = (mYLastMove - mYMove).toInt()

                when (status) {
                    Status.SCROLL_DOWN_EFFECT, Status.SCROLL_DOWN -> {
                        //根据下滑的比例，改变显示的视图
                        status = if (scrollDistance > headerHeight * changeRate) {
                            Status.SCROLL_DOWN_EFFECT
                        } else {
                            Status.SCROLL_DOWN
                        }

                        if (easyRefreshHeader != null) {
                            //滚动过程回调
                            easyRefreshHeader!!.scrolling(header, scrollDistance, headerHeight)
                        }

                        if (scrollY > 0) {
                            status = Status.NORMAL
                            if (easyRefreshHeader != null)
                                easyRefreshHeader!!.init(header)
                            resetPosition()
                            event.action = MotionEvent.ACTION_DOWN
                            dispatchTouchEvent(event)
                        }
                        //如果当前布局向下拉出头布局，并且头布局向下滚动的距离小于等于头布局的高度
                        //或者当前头布局已经拉出，但是正在回滚
                        //两种情况下都执行滚动操作
                        if (scrollY <= 0 && scrollY >= -headerHeight || scrollY <= 0 && deltaY > 0) {
                            doMove((deltaY / moveRate).toInt())
                        }
                    }
                    Status.SCROLL_UP -> {
                        if (scrollY < 0) {
                            status = Status.NORMAL
                            if (easyRefreshFooter != null)
                                easyRefreshFooter!!.loadFinish(footer)
                            footer!!.visibility = View.INVISIBLE
                            scrollTo(0, 0)
                            event.action = MotionEvent.ACTION_DOWN
                            dispatchTouchEvent(event)
                        }
                        if (easyRefreshFooter != null)
                            easyRefreshFooter!!.scrolling(footer, scrollDistance, footerHeight)

                        if (scrollY >= 0 && scrollY <= footerHeight || scrollY >= 0 && deltaY < 0) {
                            doMove((deltaY / moveRate).toInt())
                        }
                    }
                    else -> {
                    }
                }
                mYLastMove = mYMove
            }
            MotionEvent.ACTION_UP -> {
                when (status) {
                    Status.SCROLL_DOWN_EFFECT -> showRefresh()
                    Status.REFRESHING -> {
                    }
                    Status.SCROLL_UP, Status.LOADING -> showLoading()
                    Status.SCROLL_DOWN, Status.NORMAL -> {
                        closeLoad()
                        closeRefresh()
                    }
                }
                //如果当前处于正常状态，则相应点击事件
                if (status == Status.NORMAL)
                    performClick()
            }
        }
        return true
    }

    private fun resetPosition() {
        when (displayMode) {
            DisplayMode.OVERLAP -> {
                contentView?.layout(0, 0, width, contentViewHeight)
            }
            DisplayMode.LINEAR -> {
                scrollTo(0, 0)
            }
        }
    }

    private fun doMove(mb: Int) {
        val moveBy = (mb * damping).toInt()
        damping *= DAMPING_SPAN
        when (displayMode) {
            DisplayMode.OVERLAP -> {
                contentView?.let {
                    it.layout(0, it.top - moveBy, width, it.top - moveBy + contentViewHeight)
                }
            }

            DisplayMode.LINEAR -> {
                scrollBy(0, moveBy)
            }
        }
    }

    fun dealMulTouchEvent(ev: MotionEvent) {
        val action = MotionEventCompat.getActionMasked(ev)
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                val pointerIndex = MotionEventCompat.getActionIndex(ev)
                mYDown = ev.getY(pointerIndex)
                mYLastMove = mYDown
                mActivePointerId = ev.getPointerId(0)
            }
            MotionEvent.ACTION_MOVE -> {
                val pointerIndex = ev.findPointerIndex(mActivePointerId)
                mYMove = ev.getY(pointerIndex)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> mActivePointerId = MotionEvent.INVALID_POINTER_ID
            MotionEvent.ACTION_POINTER_DOWN -> {
                val pointerIndex = MotionEventCompat.getActionIndex(ev)
                val pointerId = ev.getPointerId(pointerIndex)
                //有新的点按下，就将新点算作有效点
                if (pointerId != mActivePointerId) {
                    val distance = mYLastMove - mYDown
                    mYLastMove = ev.getY(pointerIndex)
                    mYDown = mYLastMove - distance
                    mActivePointerId = ev.getPointerId(pointerIndex)
                }
            }
            MotionEvent.ACTION_POINTER_UP -> {
                val pointerIndex = MotionEventCompat.getActionIndex(ev)
                val pointerId = ev.getPointerId(pointerIndex)
                //如果有效点抬起，就重新选择有效点
                if (pointerId == mActivePointerId) {
                    val newPointerIndex = if (pointerIndex == 0) 1 else 0
                    val distance = mYLastMove - mYDown
                    mYLastMove = ev.getY(newPointerIndex)
                    mYDown = mYLastMove - distance
                    mActivePointerId = ev.getPointerId(newPointerIndex)
                }
            }
        }
    }

    private fun showLoading() {
        val dy = footerHeight - scrollY
        status = Status.LOADING
        mScroller.startScroll(0, scrollY, 0, dy)
        invalidate()
        footer!!.visibility = View.VISIBLE
        if (easyRefreshFooter != null)
            easyRefreshFooter!!.loadFinish(footer)
        callLoad()
    }

    private fun showRefresh() {
        val dy = -headerHeight - scrollY
        status = Status.REFRESHING
        mScroller.startScroll(0, scrollY, 0, dy)
        invalidate()
        if (easyRefreshHeader != null)
            easyRefreshHeader!!.refreshing(header)
        callRefresh()
    }

    private fun callLoad() {
        innerIsLoadAble = false
        innerIsRefreshAble = false
        if (loadListener != null)
            loadListener!!.onLoad()
    }

    private fun callRefresh() {
        innerIsRefreshAble = false
        innerIsLoadAble = false
        if (refreshListener != null)
            refreshListener!!.onRefresh()
    }


    override fun computeScroll() {
        //如果还在滚动过程中
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX, mScroller.currY)
            invalidate()
        }
    }


    interface OnRefreshListener {
        fun onRefresh()
    }

    interface OnLoadListener {
        fun onLoad()
    }

    //////////////////////////////////////////////////////////////////
    ///////  以下是对外的接口
    //////////////////////////////////////////////////////////////////

    /**
     * 设置刷新监听
     *
     * @param listener
     */
    fun setOnRefreshListener(listener: OnRefreshListener) {
        this.refreshListener = listener
    }

    /**
     * 设置加载更多监听
     *
     * @param loadListener
     */
    fun setOnLoadListener(loadListener: OnLoadListener) {
        this.loadListener = loadListener
    }

    /**
     * 结束刷新
     */
    fun closeRefresh() {
        val dy = -scrollY
        status = Status.NORMAL
        innerIsRefreshAble = true
        innerIsLoadAble = true
        scrollToInit(dy)
        if (easyRefreshHeader != null)
            easyRefreshHeader!!.refreshFinish(header)
    }

    private fun scrollToInit(dy: Int) {
        damping = 1.0
        when (displayMode) {
            DisplayMode.OVERLAP -> {
                contentView?.let { c->
                    c.doAnim(floatArrayOf(c.top.toFloat(), 0f)) { value ->
                        c.layout(0, value.toInt(), width, contentViewHeight)
                    }
                }
            }
            DisplayMode.LINEAR -> {
                mScroller.startScroll(0, scrollY, 0, dy)
                invalidate()
            }
        }
    }

    /**
     * 结束加载
     */
    fun closeLoad() {
        val dy = -scrollY
        status = Status.NORMAL
        innerIsRefreshAble = true
        innerIsLoadAble = true
        scrollToInit(dy)
        if (easyRefreshFooter != null)
            easyRefreshFooter!!.loadFinish(footer)
    }

    /**
     * 设置使用模式
     *
     * @param mode
     */
    fun setMode(mode: Mode) {
        this.mode = mode
    }


    /**
     * 设置头尾滚动的距离与实际手滑动距离的比例
     * 如果为1，则表示手移动多少头就滑动多少
     *
     * @param moveRate
     */
    fun setMoveRate(moveRate: Float) {
        this.moveRate = moveRate
    }

    /**
     * 刷新加载功能是否可用
     *
     * @param enable
     */
    fun setEnable(enable: Boolean) {
        this.enable = enable
    }

    /**
     * 是否允许上拉加载更多
     *
     * @param loadAble
     */
    fun setLoadAble(loadAble: Boolean) {
        isLoadAble = loadAble
    }

    /**
     * 是否允许下拉刷新
     *
     * @param refreshAble
     */
    fun setRefreshAble(refreshAble: Boolean) {
        isRefreshAble = refreshAble
    }

    /**
     * 设置好自定义头布局
     *
     * @param easyRefreshHeader
     */
    fun setHeader(easyRefreshHeader: EasyRefreshHeaderHandler) {
        easyRefreshHeader.getView(inflater, this)
        this.easyRefreshHeader = easyRefreshHeader
        this.header = getChildAt(childCount - 1)
        contentView?.bringToFront()
        requestLayout()
        invalidate()
    }

    /**
     * 设置自定义尾布局
     *
     * @param easyRefreshFooter
     */
    fun setFooter(easyRefreshFooter: EasyRefreshFooterHandler) {
        easyRefreshFooter.getView(inflater, this)
        this.easyRefreshFooter = easyRefreshFooter
        this.footer = getChildAt(childCount - 1)
        this.footer!!.visibility = View.INVISIBLE
        requestLayout()
    }

    /**
     * 是否数据充满布局，才允许上拉加载更多
     * @param loadOnlyDataFullScreen
     */
    fun setLoadOnlyDataFullScreen(loadOnlyDataFullScreen: Boolean) {
        this.loadOnlyDataFullScreen = loadOnlyDataFullScreen
    }

    fun refresh(isRefresh: Boolean) {
        if (isRefresh) {
            showRefresh()
        } else {
            closeRefresh()
        }
    }

    fun load(isLoad: Boolean) {
        if (isLoad) {
            showLoading()
        } else {
            closeLoad()
        }
    }
}
