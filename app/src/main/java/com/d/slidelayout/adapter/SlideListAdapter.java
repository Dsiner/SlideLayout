package com.d.slidelayout.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.d.lib.slidelayout.SlideLayout;
import com.d.lib.slidelayout.SlideManager;
import com.d.lib.xrv.adapter.CommonAdapter;
import com.d.lib.xrv.adapter.CommonHolder;
import com.d.slidelayout.R;
import com.d.slidelayout.bean.Bean;

import java.util.List;

/**
 * SlideListAdapter
 * Created by D on 2017/5/6.
 */
public class SlideListAdapter extends CommonAdapter<Bean> {
    private SlideManager manager;

    public SlideListAdapter(Context context, List<Bean> datas, int layoutId) {
        super(context, datas, layoutId);
        manager = new SlideManager();
    }

    @Override
    public void convert(final int position, CommonHolder holder, final Bean item) {
        holder.setText(R.id.tv_content, item.content);
        final SlideLayout slSlide = holder.getView(R.id.sl_slide);
        slSlide.setOpen(item.isOpen, false);
        slSlide.setOnStateChangeListener(new SlideLayout.OnStateChangeListener() {
            @Override
            public void onChange(SlideLayout layout, boolean isOpen) {
                item.isOpen = isOpen;
                manager.onChange(layout, isOpen);
            }

            @Override
            public boolean closeAll(SlideLayout layout) {
                return manager.closeAll(layout);
            }
        });
        holder.setViewOnClickListener(R.id.tv_stick, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slSlide.setOpen(false, false);
                mDatas.remove(position);
                mDatas.add(0, item);
                notifyDataSetChanged();
            }
        });
        holder.setViewOnClickListener(R.id.tv_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slSlide.close();
                mDatas.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mDatas.size());
            }
        });
        holder.setViewOnClickListener(R.id.sl_slide, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slSlide.isOpen()) {
                    slSlide.close();
                    return;
                }
                Toast.makeText(mContext, "Click at: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
