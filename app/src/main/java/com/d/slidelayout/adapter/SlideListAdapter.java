package com.d.slidelayout.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.d.lib.pulllayout.rv.adapter.CommonAdapter;
import com.d.lib.pulllayout.rv.adapter.CommonHolder;
import com.d.lib.slidelayout.SlideLayout;
import com.d.lib.slidelayout.SlideManager;
import com.d.slidelayout.R;
import com.d.slidelayout.bean.Bean;

import java.util.List;

/**
 * SlideListAdapter
 * Created by D on 2017/5/6.
 */
public class SlideListAdapter extends CommonAdapter<Bean> {
    private final SlideManager mSlideManager;

    public SlideListAdapter(Context context, List<Bean> datas, int layoutId) {
        super(context, datas, layoutId);
        mSlideManager = new SlideManager();
    }

    @Override
    public void convert(final int position, CommonHolder holder, final Bean item) {
        holder.setText(R.id.tv_content, item.content);
        final SlideLayout sl_slide = holder.getView(R.id.sl_slide);
        sl_slide.setOpen(item.isOpen, false);
        sl_slide.setOnStateChangeListener(new SlideLayout.OnStateChangeListener() {
            @Override
            public void onChange(SlideLayout layout, boolean isOpen) {
                item.isOpen = isOpen;
                mSlideManager.onChange(layout, isOpen);
            }

            @Override
            public boolean closeAll(SlideLayout layout) {
                return mSlideManager.closeAll(layout);
            }
        });
        holder.setOnClickListener(R.id.tv_stick, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sl_slide.setOpen(false, false);
                mDatas.remove(position);
                mDatas.add(0, item);
                notifyDataSetChanged();
            }
        });
        holder.setOnClickListener(R.id.tv_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sl_slide.close();
                mDatas.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mDatas.size());
            }
        });
        holder.setOnClickListener(R.id.sl_slide, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sl_slide.isOpen()) {
                    sl_slide.close();
                    return;
                }
                Toast.makeText(mContext, "Click at: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
