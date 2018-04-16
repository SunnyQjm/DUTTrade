package com.j.ming.duttrade.activity.base

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class DUTTradeFragment: Fragment(){
    /**
     * 当前Fragment是否可见
     */
    protected var visible = false

    /**
     * 是否是第一次
     */
    protected var firstCreateView = true

    protected var firstLoadData = true

    /**
     * 视图是否已经构建完毕
     */
    protected var prepare = false


    protected var mView: View? = null

    companion object {
        const val STATE_SAVE_IS_HIDDEN: String = "STATE_SAVE_IS_HIDDEN"
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //当页面重启时，Fragment会被保存回复，而此时再加载Fragment便会导致重叠
        if (savedInstanceState != null) {
            val isHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN)
            val ft = fragmentManager?.beginTransaction()
            if (isHidden)
                ft?.hide(this)
            else
                ft?.show(this)
            ft?.commit()
        }
    }

    abstract fun getRes(): Int
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = mView?:inflater.inflate(getRes(), container, false)

        //防止被重复添加
        mView?.parent?.let {
            (it as ViewGroup).removeView(mView)
        }
        return mView
    }

    abstract fun initView()
    /**
     * 当视图创建完成后会调用这个回调
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(firstCreateView){
            firstCreateView = false
            initView()
        }
        //视图加载完毕
        prepare = true
    }

    /**
     * 下面的函数由系统调用
     * 在Fragment可见时加载数据
     *
     * @param isVisibleToUser
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser){
            visible = true
            onVisible()
        } else {
            visible = false
            onInVisible()
        }
    }

    open fun onVisible(){
        if(prepare && firstLoadData){
            firstLoadData = false
            initialLoadData()
        }
    }

    abstract fun initialLoadData()

    open fun onInVisible(){

    }


    protected var mListener: OnFragmentInteractionListener? = null
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri?)
    }
}