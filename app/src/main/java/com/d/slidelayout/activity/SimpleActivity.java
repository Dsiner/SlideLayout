package com.d.slidelayout.activity;

import android.view.View;
import android.widget.Toast;

import com.d.lib.common.component.mvp.MvpBasePresenter;
import com.d.lib.common.component.mvp.MvpView;
import com.d.lib.common.component.mvp.app.BaseActivity;
import com.d.lib.slidelayout.SlideLayout;
import com.d.slidelayout.R;

public class SimpleActivity extends BaseActivity<MvpBasePresenter> implements View.OnClickListener {
    private SlideLayout sl_slide;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_content:
                if (sl_slide.isOpen()) {
                    sl_slide.close();
                    return;
                }
                Toast.makeText(SimpleActivity.this, "Content", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_stick:
                sl_slide.close();
                Toast.makeText(SimpleActivity.this, "Stick", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_delete:
                sl_slide.close();
                Toast.makeText(SimpleActivity.this, "Delete", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_simple;
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
        sl_slide = (SlideLayout) findViewById(R.id.sl_slide);
        sl_slide.findViewById(R.id.tv_content).setOnClickListener(this);
        sl_slide.findViewById(R.id.tv_stick).setOnClickListener(this);
        sl_slide.findViewById(R.id.tv_delete).setOnClickListener(this);
    }

    @Override
    protected void init() {

    }
}
