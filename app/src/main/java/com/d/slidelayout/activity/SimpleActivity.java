package com.d.slidelayout.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.d.lib.slidelayout.SlideLayout;
import com.d.slidelayout.R;

public class SimpleActivity extends AppCompatActivity implements View.OnClickListener {
    private SlideLayout slSlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        bindView();
    }

    private void bindView() {
        slSlide = (SlideLayout) findViewById(R.id.sl_slide);
        slSlide.findViewById(R.id.tv_content).setOnClickListener(this);
        slSlide.findViewById(R.id.tv_stick).setOnClickListener(this);
        slSlide.findViewById(R.id.tv_delete).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_content:
                if (slSlide.isOpen()) {
                    slSlide.close();
                    return;
                }
                Toast.makeText(SimpleActivity.this, "Content", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_stick:
                slSlide.close();
                Toast.makeText(SimpleActivity.this, "Stick", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_delete:
                slSlide.close();
                Toast.makeText(SimpleActivity.this, "Delete", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
