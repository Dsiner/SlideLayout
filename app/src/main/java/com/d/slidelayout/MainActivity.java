package com.d.slidelayout;

import android.content.Intent;
import android.view.View;

import com.d.lib.common.component.mvp.MvpBasePresenter;
import com.d.lib.common.component.mvp.MvpView;
import com.d.lib.common.component.mvp.app.BaseActivity;
import com.d.lib.common.util.ViewHelper;
import com.d.slidelayout.activity.ListActivity;
import com.d.slidelayout.activity.SimpleActivity;

public class MainActivity extends BaseActivity<MvpBasePresenter>
        implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_simple:
                startActivity(new Intent(MainActivity.this, SimpleActivity.class));
                break;

            case R.id.btn_list:
                startActivity(new Intent(MainActivity.this, ListActivity.class));
                break;
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public MvpBasePresenter getPresenter() {
        return new MvpBasePresenter(getApplicationContext());
    }

    @Override
    protected MvpView getMvpView() {
        return this;
    }

    @Override
    protected void bindView() {
        ViewHelper.setOnClickListener(this, this,
                R.id.btn_simple,
                R.id.btn_list
        );
    }

    @Override
    protected void init() {
    }
}
