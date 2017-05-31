package com.d.slidelayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.d.lib.slidelayout.SlideLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SlideLayout slSlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slSlide = (SlideLayout) findViewById(R.id.sl_slide);
        findViewById(R.id.tv_content).setOnClickListener(this);
        findViewById(R.id.tv_stick).setOnClickListener(this);
        findViewById(R.id.tv_delete).setOnClickListener(this);
        findViewById(R.id.btn_list).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_content:
                if (slSlide.isOpen()) {
                    slSlide.close();
                    return;
                }
                Toast.makeText(MainActivity.this, "content", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_stick:
                slSlide.close();
                Toast.makeText(MainActivity.this, "stick", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_delete:
                slSlide.close();
                Toast.makeText(MainActivity.this, "delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_list:
                startActivity(new Intent(MainActivity.this, ListActivity.class));
                break;
        }
    }
}
