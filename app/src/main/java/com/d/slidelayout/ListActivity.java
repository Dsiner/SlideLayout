package com.d.slidelayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.d.slidelayout.adapter.SlideListAdapter;
import com.d.slidelayout.bean.Bean;
import com.d.xrv.LRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        LRecyclerView lrvList = (LRecyclerView) findViewById(R.id.lrv_list);
        lrvList.setAdapter(new SlideListAdapter(this, getDatas(), R.layout.adapter_slide));
    }

    private List<Bean> getDatas() {
        List<Bean> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Bean b = new Bean("" + i, false);
            datas.add(b);
        }
        return datas;
    }
}
