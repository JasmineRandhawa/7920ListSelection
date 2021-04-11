package com.example.newdesign;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.database.Observable;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.OverScroller;

import java.util.ArrayList;
import java.util.List;

public class CitiesListView extends ViewGroup {

    private final int[] mScrollOffset = new int[2];
    private int mScrollY;
    private int mScrollX;
    private int mTotalLength;
    private int mOverlapGaps=80;
    private int mNumBottomShow=3;
    private OuterListAdaptor outerListAdaptor;
    private int selectedIndex = -1;
    private int mShowHeight;
    private final ViewDataObserver mObserver = new ViewDataObserver();
    private final List<ListItemView> listItemViews;
    private final OverScroller mScroller;
    private int mLastMotionY;
    private boolean mIsBeingDragged = false;
    private VelocityTracker mVelocityTracker;
    private int activePointer = -1;
    private boolean mScrollEnable = true;
    private int mNestedYOffset;

    public CitiesListView(Context context) {
        this(context, null);
    }

    public CitiesListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CitiesListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        listItemViews = new ArrayList<>();
        mScroller = new OverScroller(getContext());
    }

    public int getOverlapGaps() {
        return mOverlapGaps;
    }

    public int getNumBottomShow() {
        return mNumBottomShow;
    }

    public int getOverlapGapsCollapse() {
        return mOverlapGaps;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }
    public void setSelectedIndex(int pos) {
        selectedIndex = pos;
    }
    public void setScrollEnable(boolean scrollEnable) {
        mScrollEnable = scrollEnable;
    }

    public int getShowHeight() {
        return mShowHeight;
    }

    public int getTotalLength() {
        return mTotalLength;
    }

    public int getViewScrollY() {
        return mScrollY;
    }

    public void setViewScrollY(int y) {
        scrollViewTo(mScrollX, y);
    }

    public int getViewScrollX() {
        return mScrollX;
    }

    public void setViewScrollX(int x) {
        scrollViewTo(x, mScrollY);
    }


    public void setAdapter(OuterListAdaptor listAdaptor) {
        outerListAdaptor = listAdaptor;
        outerListAdaptor.registerObserver(mObserver);
        refreshView();
        if (selectedIndex != -1) {
            updateSelectPosition(selectedIndex);
        }
        setViewScrollY(0);
        requestLayout();
    }

    public void updateSelectPosition(final int selectPosition) {
        post(new Runnable() {
            @Override
            public void run() {
                View parentView = (View) getParent();
                mShowHeight = parentView.getMeasuredHeight() - parentView.getPaddingTop() - parentView.getPaddingBottom();
                outerListAdaptor.itemClick(listItemViews.get(selectPosition), selectedIndex);
            }
        });
    }


    ListItemView getListContainer(int i) {
        if (i == -1) return null;
        ListItemView listItemView;
        if (listItemViews.size() <= i || outerListAdaptor.getItemViewType(i) != listItemViews.get(i).itemViewType) {
            listItemView = outerListAdaptor.createView(this, outerListAdaptor.getItemViewType(i));
            listItemViews.add(listItemView);
        } else {
            listItemView = listItemViews.get(i);
        }
        return listItemView;
    }


    private static int clamp(int n, int my, int child) {
        if (my >= child || n < 0) {
            return 0;
        }
        if ((my + n) > child) {
            return child - my;
        }
        return n;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View parentView = (View) getParent();
        mShowHeight = parentView.getMeasuredHeight() - parentView.getPaddingTop() - parentView.getPaddingBottom();
        int maxWidth = 0;
        mTotalLength = 0;
        mTotalLength += getPaddingTop() + getPaddingBottom();
        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
            final int totalLength = mTotalLength;
            final LayoutParams lp =
                    (LayoutParams) child.getLayoutParams();
            if (lp.mHeaderHeight == -1) lp.mHeaderHeight = child.getMeasuredHeight();
            final int childHeight = lp.mHeaderHeight;
            mTotalLength = Math.max(totalLength, totalLength + childHeight + lp.topMargin +
                    lp.bottomMargin);
            mTotalLength -= mOverlapGaps * 2;
            final int margin = lp.leftMargin + lp.rightMargin;
            final int measuredWidth = child.getMeasuredWidth() + margin;
            maxWidth = Math.max(maxWidth, measuredWidth);
        }

        mTotalLength += mOverlapGaps * 2;
        int heightSize = mTotalLength;
        heightSize = Math.max(heightSize, mShowHeight);
        int heightSizeAndState = resolveSizeAndState(heightSize, heightMeasureSpec, 0);
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                heightSizeAndState);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childTop = getPaddingTop();
        int childLeft = getPaddingLeft();

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            final int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            final LayoutParams lp =
                    (LayoutParams) child.getLayoutParams();
            childTop += lp.topMargin;
            if (i != 0) {
                childTop -= mOverlapGaps * 2;
                child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            } else {
                child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            }
            childTop += lp.mHeaderHeight;
        }
    }

    public void scrollViewTo(int x, int y) {
        x = clamp(x, this.getWidth() - this.getPaddingRight() - this.getPaddingLeft(), this.getWidth());
        y = clamp(y, this.getShowHeight(), this.getTotalLength());
        mScrollY = y;
        mScrollX = x;
        for (int i = 0; i < this.getChildCount(); i++) {
            View view = this.getChildAt(i);
            if (view.getTop() - mScrollY < this.getChildAt(0).getY()) {
                view.setTranslationY(this.getChildAt(0).getY() - view.getTop());
            } else if (view.getTop() - mScrollY > view.getTop()) {
                view.setTranslationY(0);
            } else {
                view.setTranslationY(-mScrollY);
            }
        }
    }



    private void refreshView() {
        removeAllViews();
        listItemViews.clear();
        for (int i = 0; i < outerListAdaptor.getItemCount(); i++) {
            ListItemView holder = getListContainer(i);
            holder.position = i;
            holder.onOuterItemExpansion(i == selectedIndex);
            addView(holder.itemView);
            setClickAnimator(holder, i);
            outerListAdaptor.bindItemContainer(holder, i);
        }
        requestLayout();
    }



    private void setClickAnimator(final ListItemView listItemView, final int position) {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedIndex == -1) return;
                performItemClick(listItemViews.get(getSelectedIndex()));
            }
        });
        listItemView.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                performItemClick(listItemView);
            }
        });
    }
    public void performItemClick(ListItemView listItemView) {
        View parentView = (View) getParent();
        mShowHeight = parentView.getMeasuredHeight() - parentView.getPaddingTop() - parentView.getPaddingBottom();
        outerListAdaptor.itemClick(listItemView, listItemView.position);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        if ((action == MotionEvent.ACTION_MOVE) && (mIsBeingDragged)) {
            return true;
        }
        if (getViewScrollY() == 0 && !canScrollVertically(1)) {
            return false;
        }

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE: {
                final int activePointerId = activePointer;
                if (activePointerId == -1)
                    break;

                final int pointerIndex = ev.findPointerIndex(activePointerId);
                if (pointerIndex == -1)
                    break;

                final int y = (int) ev.getY(pointerIndex);
                final int yDiff = Math.abs(y - mLastMotionY);
                if (yDiff > 16) {
                    mIsBeingDragged = true;
                    mLastMotionY = y;
                    if (mVelocityTracker == null) {
                        mVelocityTracker = VelocityTracker.obtain();
                    }
                    mVelocityTracker.addMovement(ev);
                    mNestedYOffset = 0;
                    final ViewParent parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
            }

            case MotionEvent.ACTION_DOWN: {
                final int y = (int) ev.getY();
                mLastMotionY = y;
                activePointer = ev.getPointerId(0);
                if (mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain();
                } else {
                    mVelocityTracker.clear();
                }
                mVelocityTracker.addMovement(ev);
                mIsBeingDragged = !mScroller.isFinished();
                break;
            }

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsBeingDragged = false;
                activePointer = -1;
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                if (mScroller.springBack(getViewScrollX(), getViewScrollY(), 0, 0, 0, getScrollRange())) {
                    postInvalidate();
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
        }
        if (!mScrollEnable) {
            mIsBeingDragged = false;
        }
        return mIsBeingDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!mIsBeingDragged) {
            super.onTouchEvent(ev);
        }
        if (!mScrollEnable) {
            return true;
        }
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }

        MotionEvent vtev = MotionEvent.obtain(ev);

        final int actionMasked = ev.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            mNestedYOffset = 0;
        }
        vtev.offsetLocation(0, mNestedYOffset);

        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN: {
                if (getChildCount() == 0)
                    return false;
                if ((mIsBeingDragged = !mScroller.isFinished())) {
                    final ViewParent parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                }
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastMotionY = (int) ev.getY();
                activePointer = ev.getPointerId(0);
                break;
            }
            case MotionEvent.ACTION_MOVE:
                final int activePointerIndex = ev.findPointerIndex(activePointer);
                if (activePointerIndex == -1)
                    break;

                final int y = (int) ev.getY(activePointerIndex);
                int deltaY = mLastMotionY - y;
                if (!mIsBeingDragged && Math.abs(deltaY) > 16) {
                    final ViewParent parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    mIsBeingDragged = true;
                    if (deltaY > 0) {
                        deltaY -= 16;
                    } else {
                        deltaY += 16;
                    }
                }
                if (mIsBeingDragged) {
                    mLastMotionY = y - mScrollOffset[1];
                    scrollViewTo(0, deltaY + getViewScrollY());
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mIsBeingDragged) {
                    final VelocityTracker velocityTracker = mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, 16000);
                    int initialVelocity = (int) velocityTracker.getYVelocity(activePointer);
                    if (getChildCount() > 0) {
                        if ((Math.abs(initialVelocity) > 100)) {
                            fling(-initialVelocity);
                        } else {
                            if (mScroller.springBack(getViewScrollX(), getViewScrollY(), 0, 0, 0,
                                    getScrollRange())) {
                                postInvalidate();
                            }
                        }
                        activePointer = -1;
                    }
                }
                endDrag();
                break;
            case MotionEvent.ACTION_CANCEL:
                if (mIsBeingDragged && getChildCount() > 0) {
                    if (mScroller.springBack(getViewScrollX(), getViewScrollY(), 0, 0, 0, getScrollRange())) {
                        postInvalidate();
                    }
                    activePointer = -1;
                }
                endDrag();
                break;
            case MotionEvent.ACTION_POINTER_DOWN: {
                final int index = ev.getActionIndex();
                mLastMotionY = (int) ev.getY(index);
                activePointer = ev.getPointerId(index);
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                mLastMotionY = (int) ev.getY(ev.findPointerIndex(activePointer));
                break;
        }

        if (mVelocityTracker != null) {
            mVelocityTracker.addMovement(vtev);
        }
        vtev.recycle();
        return true;
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >>
                MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        final int pointerId = ev.getPointerId(pointerIndex);
        if (pointerId == activePointer) {
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mLastMotionY = (int) ev.getY(newPointerIndex);
            activePointer = ev.getPointerId(newPointerIndex);
            if (mVelocityTracker != null) {
                mVelocityTracker.clear();
            }
        }
    }

    private int getScrollRange() {
        int scrollRange = 0;
        if (getChildCount() > 0) {
            scrollRange = Math.max(0,
                    mTotalLength - mShowHeight);
        }
        return scrollRange;
    }

    @Override
    protected int computeVerticalScrollRange() {
        final int count = getChildCount();
        final int contentHeight = mShowHeight;
        if (count == 0) {
            return contentHeight;
        }

        int scrollRange = mTotalLength;
        final int scrollY = getViewScrollY();
        final int overscrollBottom = Math.max(0, scrollRange - contentHeight);
        if (scrollY < 0) {
            scrollRange -= scrollY;
        } else if (scrollY > overscrollBottom) {
            scrollRange += scrollY - overscrollBottom;
        }

        return scrollRange;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY,
                                  boolean clampedX, boolean clampedY) {
        if (!mScroller.isFinished()) {
            final int oldX = getViewScrollX();
            final int oldY = getViewScrollY();
            setViewScrollX(scrollX);
            setViewScrollY(scrollY);
            onScrollChanged(getViewScrollX(), getViewScrollY(), oldX, oldY);
            if (clampedY) {
                mScroller.springBack(getViewScrollX(), getViewScrollY(), 0, 0, 0, getScrollRange());
            }
        } else {
            super.scrollTo(scrollX, scrollY);
        }
    }

    @Override
    protected int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollViewTo(0, mScroller.getCurrY());
            postInvalidate();
        }
    }

    public void fling(int velocityY) {
        if (getChildCount() > 0) {
            int height = mShowHeight;
            int bottom = mTotalLength;
            mScroller.fling(getViewScrollX(), getViewScrollY(), 0, velocityY, 0, 0, 0,
                    Math.max(0, bottom - height), 0, 0);
            postInvalidate();
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        if (getChildCount() > 0) {
            x = clamp(x, getWidth() - getPaddingRight() - getPaddingLeft(), getWidth());
            y = clamp(y, mShowHeight, mTotalLength);
            if (x != getViewScrollX() || y != getViewScrollY()) {
                super.scrollTo(x, y);
            }
        }
    }


    private void endDrag() {
        mIsBeingDragged = false;
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }


    public static class LayoutParams extends MarginLayoutParams {

        public int mHeaderHeight = 240;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width , height );
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    public static abstract class Adapter<VH extends ListItemView> {
        private final AdapterDataObservable mObservable = new AdapterDataObservable();
        ListItemView createView(ViewGroup parent, int viewType) {
            ListItemView listItemView = onCreateView(parent, viewType);
            listItemView.itemViewType = viewType;
            return listItemView;
        }

        public abstract VH onCreateView(ViewGroup parent, int viewType);


        public void bindItemContainer(ListItemView container, int index) {
            onBindItemContainer(container, index);
        }

        public abstract void onBindItemContainer(ListItemView container, int position);

        public abstract int getItemCount();

        public int getItemViewType(int position) {
            return 0;
        }

        public final void notifyDataSetChanged() {
            mObservable.notifyChanged();
        }

        public void registerObserver(AdapterDataObserver observer) {
            mObservable.registerObserver(observer);
        }
    }

    public static abstract class ListItemView {

        public View itemView;
        int itemViewType;
        int position;

        public ListItemView(View view) {
            itemView = view;
        }

        public Context getContext() {
            return itemView.getContext();
        }

        public abstract void onOuterItemExpansion(boolean b);

        protected void onAnimationRepeat(boolean isSelection, int currentState) {

        }
    }


    public static class AdapterDataObservable extends Observable<AdapterDataObserver> {
        public boolean hasObservers() {
            return !mObservers.isEmpty();
        }

        public void notifyChanged() {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onChanged();
            }
        }
    }

    public static abstract class AdapterDataObserver {
        public void onChanged() {
        }
    }

    private class ViewDataObserver extends AdapterDataObserver {
        @Override
        public void onChanged() {
            refreshView();
        }
    }

}