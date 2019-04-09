package com.xlkj.beautifulpicturehouse.common.mvp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2018/1/20.
 * 3.创建一个基类的Activity，声明一个创建Presenter的抽象方法，因为要帮子类去绑定和解绑那么就需要拿到子类的Presenter才行，
 * 但是又不能随便一个类都能绑定的，因为只有基类的Presenter中才定义了绑定和解绑的方法，所以同样的在类上可以声明泛型在方法上使用泛型来达到目的
 */

public abstract class AbstractMvpActivity<V extends IMvpBaseView, P extends AbstractMvpPersenter<V>> extends AppCompatActivity implements IMvpBaseView {
    private P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建Presenter
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (presenter == null) {
            throw new NullPointerException("presenter 不能为空!");
        }
        //绑定view
        presenter.attachMvpView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除绑定
        if (presenter != null) {
            presenter.detachMvpView();
        }
    }

    /**
     * 创建Presenter
     *
     * @return 子类自己需要的Presenter
     */
    protected abstract P createPresenter();

    /**
     * 获取Presenter
     *
     * @return 返回子类创建的Presenter
     */
    public P getPresenter() {
        return presenter;
    }
}
