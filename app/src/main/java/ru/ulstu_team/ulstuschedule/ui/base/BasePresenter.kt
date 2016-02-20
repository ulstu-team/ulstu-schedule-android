package ru.ulstu_team.ulstuschedule.ui.base

import io.realm.Realm

open class BasePresenter<T : MvpView> : Presenter<T> {

    var mvpView: T? = null
        private set

    override fun attachView(mvpView: T) {
        this.mvpView = mvpView
    }

    override fun detachView() {
        mvpView = null
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
