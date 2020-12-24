package com.d.lib.slidelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.ArrayList;

/**
 * SlideLayout
 * Created by D on 2017/5/19.
 */
public class SlideLayout extends ViewGroup {
    private int mWidth;
    private int mHeight;

    private final ArrayList<View> mMatchParentChildren = new ArrayList<>(1);
    private Scroller mScroller;
    private int mLeftBorder;
    private int mRightBorder;
    private int mTouchSlop;
    private int mSlideSlop;
    private int mDuration;

    // TouchEvent_ACTION_DOWN coordinates (x, y)
    private float mTouchX, mTouchY;

    // TouchEvent last coordinate (x, y)
    private float mLastTouchX;
    private boolean mIsDragging;
    private boolean mIsOpen;
    private boolean mIsEnable;
    private OnStateChangeListener mOnStateChangeListener;

    public SlideLayout(Context context) {
        this(context, null);
    }

    public SlideLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypedArray(context, attrs);
        init(context);
    }

    private void initTypedArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlideLayout);
        mSlideSlop = (int) typedArray.getDimension(R.styleable.SlideLayout_sl_slideSlop,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics()));
        mDuration = typedArray.getInteger(R.styleable.SlideLayout_sl_duration, 250);
        mIsEnable = typedArray.getBoolean(R.styleable.SlideLayout_sl_enable, true);
        typedArray.recycle();
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return new LayoutParams(lp);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        final boolean measureMatchParentChildren = MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY ||
                MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY;
        mMatchParentChildren.clear();

        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                final SlideLayout.LayoutParams lp = (SlideLayout.LayoutParams) child.getLayoutParams();
                maxWidth = Math.max(maxWidth,
                        child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
                maxHeight = Math.max(maxHeight,
                        child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    childState = combineMeasuredStates(childState, child.getMeasuredState());
                }

                if (measureMatchParentChildren) {
                    if (lp.width == SlideLayout.LayoutParams.MATCH_PARENT ||
                            lp.height == SlideLayout.LayoutParams.MATCH_PARENT) {
                        mMatchParentChildren.add(child);
                    }
                }
            }
        }

        // Check against our minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Check against our foreground's minimum height and width
            final Drawable drawable = getForeground();
            if (drawable != null) {
                maxHeight = Math.max(maxHeight, drawable.getMinimumHeight());
                maxWidth = Math.max(maxWidth, drawable.getMinimumWidth());
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                    resolveSizeAndState(maxHeight, heightMeasureSpec,
                            childState << MEASURED_HEIGHT_STATE_SHIFT));
        } else {
            setMeasuredDimension(mWidth, mHeight);
        }

        count = mMatchParentChildren.size();
        if (count > 1) {
            for (int i = 0; i < count; i++) {
                final View child = mMatchParentChildren.get(i);
                final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                final int childWidthMeasureSpec;
                if (lp.width == SlideLayout.LayoutParams.MATCH_PARENT) {
                    final int width = Math.max(0, getMeasuredWidth()
                            - lp.leftMargin - lp.rightMargin);
                    childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                            width, MeasureSpec.EXACTLY);
                } else {
                    childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                            lp.leftMargin + lp.rightMargin,
                            lp.width);
                }

                final int childHeightMeasureSpec;
                if (lp.height == SlideLayout.LayoutParams.MATCH_PARENT) {
                    final int height = Math.max(0, getMeasuredHeight()
                            - lp.topMargin - lp.bottomMargin);
                    childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                            height, MeasureSpec.EXACTLY);
                } else {
                    childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                            lp.topMargin + lp.bottomMargin,
                            lp.height);
                }

                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            }
        }

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        if (count <= 0) {
            return;
        }

        final int parentLeft = getPaddingLeft();
        final int parentRight = r - l - getPaddingRight();

        final int parentTop = getPaddingTop();
        final int parentBottom = b - t - getPaddingBottom();

        int left = 0, top = 0;
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();

                final int width = child.getMeasuredWidth();
                final int height = child.getMeasuredHeight();

                int childLeft;
                int childTop;

                childTop = parentTop + lp.topMargin;
                childLeft = parentLeft + lp.leftMargin;

                // Layout horizontally for each child view in the ViewGroup
                child.layout(left + childLeft, childTop, left + childLeft + width, childTop + height);

                left += childLeft + width + lp.rightMargin + getPaddingRight();
            }
        }
        // Initialize left and right boundary values
        mLeftBorder = getChildAt(0).getLeft();
        mRightBorder = getChildAt(count - 1).getRight();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            final boolean intercepted = mOnStateChangeListener != null
                    && mOnStateChangeListener.onInterceptTouchEvent(this);
            if (intercepted) {
                return false;
            }

            final float x = ev.getRawX();
            final float y = ev.getRawY();
            mLastTouchX = mTouchX = x;
            mTouchY = y;
            mIsDragging = false;
            super.dispatchTouchEvent(ev);
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!mIsEnable) {
            return super.onInterceptTouchEvent(ev);
        }
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            final float x = ev.getRawX();
            final float y = ev.getRawY();
            // Intercept child event when horizontal ACTION_MOVE value is greater than TouchSlop
            if (Math.abs(x - mTouchX) > mTouchSlop
                    && Math.abs(x - mTouchX) > Math.abs(y - mTouchY)) {
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mIsEnable) {
            return super.onTouchEvent(event);
        }

        final float x = event.getRawX();
        final float y = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (!mIsDragging
                        && Math.abs(x - mTouchX) > mTouchSlop
                        && Math.abs(x - mTouchX) > Math.abs(y - mTouchY)) {
                    // Disable parent view interception events
                    requestDisallowInterceptTouchEvent(true);
                    mIsDragging = true;
                    mLastTouchX = x;
                    return super.onTouchEvent(event);
                }
                if (mIsDragging) {
                    final int offset = (int) (mLastTouchX - x);
                    if (getScrollX() + offset < 0) {
                        setOpen(false, false);
                        mTouchX = x; // Reset touch x
                    } else if (getScrollX() + offset > mRightBorder - mWidth) {
                        setOpen(true, false);
                        mTouchX = x; // Reset touch x
                    } else {
                        scrollBy(offset, 0);
                    }
                    mLastTouchX = x;
                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mIsDragging) {
                    if (x - mTouchX < -mSlideSlop) {
                        setOpen(true, true);
                    } else if (x - mTouchX > mSlideSlop) {
                        setOpen(false, true);
                    } else {
                        setOpen(mIsOpen, true);
                    }
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    super.onTouchEvent(event);
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void smoothScrollTo(int dstX, int duration) {
        int offset = dstX - getScrollX();
        mScroller.startScroll(getScrollX(), 0, offset, 0, duration);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    public boolean isEnable() {
        return mIsEnable;
    }

    public void setEnable(boolean enable) {
        this.mIsEnable = enable;
    }

    public boolean isOpen() {
        return mIsOpen;
    }

    public void open() {
        setOpen(true, true);
    }

    public void close() {
        setOpen(false, true);
    }

    /**
     * Set on or off status
     *
     * @param open     Open or close
     * @param withAnim Whether with animation effect
     */
    public void setOpen(boolean open, boolean withAnim) {
        if (mIsOpen != open && mOnStateChangeListener != null) {
            mOnStateChangeListener.onStateChanged(this, open);
        }
        mIsOpen = open;
        int x = mIsOpen ? mRightBorder - mWidth : 0;
        int y = 0;
        if (withAnim) {
            smoothScrollTo(x, mDuration);
        } else {
            scrollTo(x, y);
        }
    }

    public void setOnStateChangeListener(OnStateChangeListener listener) {
        this.mOnStateChangeListener = listener;
    }

    public abstract static class OnStateChangeListener {

        /**
         * Implement this method to intercept all touch screen motion events.
         *
         * @param layout This layout
         */
        public boolean onInterceptTouchEvent(SlideLayout layout) {
            return false;
        }

        public abstract void onStateChanged(SlideLayout layout, boolean open);
    }

    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
