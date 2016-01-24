package ru.ulstu_team.ulstuschedule.ui.base;

import io.realm.Realm;

public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T mMvpView;
    protected Realm mRealm;

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void detachView() {
        mMvpView = null;
        mRealm.close();
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
