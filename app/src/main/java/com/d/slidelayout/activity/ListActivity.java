package com.d.slidelayout.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.d.lib.common.component.mvp.MvpBasePresenter;
import com.d.lib.common.component.mvp.MvpView;
import com.d.lib.common.component.mvp.app.BaseActivity;
import com.d.slidelayout.R;
import com.d.slidelayout.adapter.SlideListAdapter;
import com.d.slidelayout.bean.Bean;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends BaseActivity<MvpBasePresenter> {
    private RecyclerView rv_list;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_list;
    }

    @Override
    public MvpBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected MvpView getMvpView() {
        return null;
    }

    @Override
    protected void bindView() {
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
    }

    @Override
    protected void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        SlideListAdapter adapter = new SlideListAdapter(this, getDatas(), R.layout.adapter_slide);
        rv_list.setLayoutManager(layoutManager);
        rv_list.setAdapter(adapter);
    }

    private List<Bean> getDatas() {
        List<Bean> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Bean b = new Bean("" + i, false);
            list.add(b);
        }
        return list;
    }
}
