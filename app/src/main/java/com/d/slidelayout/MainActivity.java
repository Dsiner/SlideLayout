package com.d.slidelayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.d.slidelayout.activity.ListActivity;
import com.d.slidelayout.activity.SimpleActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
    }

    private void bindView() {
        findViewById(R.id.btn_simple).setOnClickListener(this);
        findViewById(R.id.btn_list).setOnClickListener(this);
    }

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
}
