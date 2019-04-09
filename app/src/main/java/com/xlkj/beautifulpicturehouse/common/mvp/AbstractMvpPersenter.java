package com.xlkj.beautifulpicturehouse.common.mvp;

/**
 * Created by Administrator on 2018/1/20.
 * 2.创建一个基类的Presenter，在类上规定View泛型，然后定义绑定和解绑的抽象方法，让子类去实现，对外在提供一个获取View的方法，
 * 让子类直接通过方法来获取View
 */

public abstract class AbstractMvpPersenter<V extends IMvpBaseView> {
    private V mMvpView;

    /**
     * 绑定V层
     * @param view
     */
    public void attachMvpView(V view){
        this.mMvpView = view;
    }

    /**
     * 解除绑定V层
     */
    public void detachMvpView(){
        mMvpView = null;
    }

    /**
     * 获取V层
     * @return
     */
    public V getmMvpView() {
        return mMvpView;
    }
}
