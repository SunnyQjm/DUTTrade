package com.j.ming.duttrade.activity.welcome

class WelcomeActivityPresenter(welcomeActivity: WelcomeActivity)
    : WelcomeActivityContract.Presenter(){
    init {
        mView = welcomeActivity
        mModel = WelcomeActivityModel(this)
    }
}