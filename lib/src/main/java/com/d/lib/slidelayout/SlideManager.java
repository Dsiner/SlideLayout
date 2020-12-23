package com.d.lib.slidelayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager for Slide List
 * Created by D on 2017/5/30.
 */
public class SlideManager {
    private final List<SlideLayout> mSlides = new ArrayList<>();

    public SlideManager() {
    }

    public void onChange(SlideLayout layout, boolean isOpen) {
        if (isOpen) {
            mSlides.add(layout);
        } else {
            mSlides.remove(layout);
        }
    }

    public boolean closeAll(SlideLayout layout) {
        boolean ret = false;
        if (mSlides.size() <= 0) {
            return false;
        }
        for (int i = 0; i < mSlides.size(); i++) {
            SlideLayout slide = mSlides.get(i);
            if (slide != null && slide != layout) {
                slide.close();
                mSlides.remove(slide); // Unnecessary
                ret = true;
                i--;
            }
        }
        return ret;
    }
}
