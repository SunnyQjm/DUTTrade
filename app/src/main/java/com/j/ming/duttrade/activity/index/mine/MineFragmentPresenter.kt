package com.j.ming.duttrade.activity.index.mine

class MineFragmentPresenter(mineFragment: MineFragment) : MineFragmentContract.Presenter(){
    init {
        mView = mineFragment
        mModel = MineFragmentModel(this)
    }
}