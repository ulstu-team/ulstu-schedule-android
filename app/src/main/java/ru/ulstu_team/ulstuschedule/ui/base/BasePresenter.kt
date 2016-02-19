package ru.ulstu_team.ulstuschedule.ui.base

import io.realm.Realm

open class BasePresenter<T : MvpView> : Presenter<T> {

    var mvpView: T? = null
        private set
    protected lateinit var mRealm: Realm

    override fun attachView(mvpView: T) {
        this.mvpView = mvpView
        mRealm = Realm.getDefaultInstance()
    }

    override fun detachView() {
        mvpView = null
        mRealm.close()
    }

    val isViewAttached: Boolean
        get() = mvpView != null

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    class MvpViewNotAttachedException :
            RuntimeException("Please call Presenter.attachView(MvpView) before"
                    + " requesting data to the Presenter")
}
