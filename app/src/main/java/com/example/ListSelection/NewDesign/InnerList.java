package com.example.ListSelection.NewDesign;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ListSelection.R;

/* Inner List View  for filtered cities list*/
public class InnerList extends ListView implements AbsListView.OnScrollListener {

    private final int innerListScrollDuration = 50;
    //class fields
    private int innerListItemHeight = 0;
    private InnerListListener innerListListener;
    private InnerListAdapter innerListAdapter;
    private boolean isInfiniteScrollingEnabled = true;
    private InnerListListener.ItemAllignment innerListAlignment = InnerListListener.ItemAllignment.Left;

    //constructor
    public InnerList(Context context) {
        this(context, null);
    }

    public InnerList(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.listViewStyle);
    }

    public InnerList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnScrollListener(this);
        setClipChildren(false);
        setInfiniteScroll(true);
    }

    //get set methods
    public int getInnerListItemHeight() {
        return (innerListItemHeight == 0 ? ((getChildAt(0) != null) ? (getChildAt(0)).getHeight() : innerListItemHeight)
                : innerListItemHeight);
    }

    public int getCenter() {
        for (int i = 0; i < getChildCount(); i++)
            if (getChildAt(i) != null && getChildAt(i).getTop() <= getHeight() / 2
                    && getChildAt(i).getTop() + getChildAt(i).getHeight() >= getHeight() / 2)
                return getFirstVisiblePosition() + i;
        return -1;
    }

    public View getCenterItem() {
        if (getCenter() != -1)
            return getChildAt(getCenter() - getFirstVisiblePosition() - 1);
        return null;
    }

    public void setInnerListAdapter(ListAdapter listAdapter) {
        innerListAdapter = new InnerListAdapter(listAdapter);
        innerListAdapter.enableInfiniteScroll(isInfiniteScrollingEnabled);
        super.setAdapter(innerListAdapter);
    }

    public void setInnerListListener(InnerListListener listViewListener) {
        this.innerListListener = listViewListener;
    }

    public void setInnerListAlignment(InnerListListener.ItemAllignment listAlignment) {
        if (innerListAlignment != listAlignment) {
            innerListAlignment = listAlignment;
            requestLayout();
        }
    }

    public void setInfiniteScroll(boolean enableInfiniteScroll) {
        isInfiniteScrollingEnabled = enableInfiniteScroll;
        if (innerListAdapter != null)
            innerListAdapter.enableInfiniteScroll(enableInfiniteScroll);
        if (isInfiniteScrollingEnabled) {
            setVerticalScrollBarEnabled(false);
            setHorizontalScrollBarEnabled(false);
        }
    }

    public void scrollFirstItemToCenter() {
        if (!isInfiniteScrollingEnabled)
            return;
        int topHeight = 0;
        if (getInnerListItemHeight() > 0)
            topHeight = getHeight() / 2 - getInnerListItemHeight() / 2;
        if (innerListAdapter.getItemCount() > 0)
            setSelectionFromTop(innerListAdapter.getItemCount(), topHeight);
    }

    public void scrollSelectedItemToCenter(int index) {
        if (!isInfiniteScrollingEnabled || innerListAdapter.getItemCount() == 0)
            return;
        index = index % innerListAdapter.getItemCount();
        int topHeight = 0;
        if (getCenter() % innerListAdapter.getItemCount() == index && getCenterItem() != null)
            topHeight = getCenterItem().getTop();
        if (getInnerListItemHeight() > 0)
            topHeight = getHeight() / 2 - getInnerListItemHeight() / 2;
        setSelectionFromTop(index + innerListAdapter.getItemCount(), topHeight);
    }

    //events
    @TargetApi(Build.VERSION_CODES.FROYO)
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (isInfiniteScrollingEnabled) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (event.getKeyCode()) {
                    case KeyEvent.KEYCODE_DPAD_DOWN:
                        smoothScrollBy(-innerListItemHeight, innerListScrollDuration);
                        return true;
                    case KeyEvent.KEYCODE_DPAD_UP:
                        smoothScrollBy(innerListItemHeight, innerListScrollDuration);
                        return true;
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (!isInTouchMode() && scrollState == SCROLL_STATE_IDLE)
            scrollSelectedItemToCenter(getCenter());
    }

    @Override
    public void onScroll(AbsListView innerList, int firstItem, int itemDisplayed, int totalItems) {
        if (!isInfiniteScrollingEnabled || this.getChildAt(0) == null || innerListAdapter.getItemCount() == 0)
            return;

        if (innerListItemHeight == 0)
            innerListItemHeight = this.getChildAt(0).getHeight();

        if (firstItem == 0)
            this.setSelectionFromTop(innerListAdapter.getItemCount(), this.getChildAt(0).getTop());

        if (totalItems == firstItem + itemDisplayed)
            this.setSelectionFromTop(firstItem - innerListAdapter.getItemCount(), this.getChildAt(0).getTop());

        if (innerListAlignment != InnerListListener.ItemAllignment.Center) {
            double vRad = (innerList.getHeight() + innerListItemHeight) / 2;
            double hRad = (innerList.getHeight() < innerList.getWidth()) ? innerList.getHeight() : innerList.getWidth();

            for (int i = 0; i < itemDisplayed; i++) {
                if (this.getChildAt(i) != null) {
                    double y = Math.min(Math.abs(innerList.getHeight() / 2 - (this.getChildAt(i).getTop() + (this.getChildAt(i).getHeight() / 2))), vRad);
                    double x = (hRad * Math.cos(Math.asin(y / vRad))) - hRad;
                    View temp = this.getChildAt(i);
                    this.getChildAt(i).post(() -> {
                        temp.setLayoutParams(new LayoutParams(200, temp.getHeight()));
                        ((TextView) temp).setTextSize(TypedValue.COMPLEX_UNIT_PX, 32);
                    });
                    this.getChildAt(i).setX((int) (this.getChildAt(i).getWidth() - x));
                    this.getChildAt(i).scrollTo((int) (int) (this.getChildAt(i).getWidth() - x) / 70, -(int) (this.getChildAt(i).getWidth() - x) / 70);
                    this.getChildAt(i).setTextAlignment(TEXT_ALIGNMENT_CENTER);
                    this.getChildAt(i).setBackgroundResource(R.drawable.inner_list_item_design);
                }
            }
        } else {
            for (int i = 0; i < itemDisplayed; i++) {
                if (this.getChildAt(i) != null) {
                    this.getChildAt(i).scrollTo(0, 0);
                    this.getChildAt(i).setX(1.5f);
                    this.getChildAt(i).setTextAlignment(TEXT_ALIGNMENT_CENTER);
                    this.getChildAt(i).setBackgroundResource(R.drawable.inner_list_selected_item_shape);
                    ((TextView) this.getChildAt(i)).setTextColor(getResources().getColor(R.color.Red));
                }
            }
        }
        if (innerListListener != null)
            innerListListener.onScrollEnd(this, firstItem, itemDisplayed, totalItems);
    }

    //listerner
    interface InnerListListener {
        void onScrollEnd(InnerList innerList, int firstItem, int displayedItems, int totalItems);

        enum ItemAllignment {Center, Left}
    }

    //Adapter
    class InnerListAdapter implements ListAdapter {
        private final ListAdapter innerlListAdapter;
        private boolean infiniteScrolling = true;

        public InnerListAdapter(ListAdapter listAdapter) {
            innerlListAdapter = listAdapter;
        }

        private void enableInfiniteScroll(boolean infiniteScroll) {
            infiniteScrolling = infiniteScroll;
        }

        public int getItemCount() {
            return innerlListAdapter.getCount();
        }

        public int goToIndex(int position) {
            return (innerlListAdapter.getCount() == 0) ? 0 : position % innerlListAdapter.getCount();
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
            innerlListAdapter.registerDataSetObserver(observer);
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
            innerlListAdapter.unregisterDataSetObserver(observer);
        }

        @Override
        public int getCount() {
            return (infiniteScrolling) ? innerlListAdapter.getCount() * 10 : innerlListAdapter.getCount();
        }

        @Override
        public Object getItem(int index) {
            return innerlListAdapter.getItem(this.goToIndex(index));
        }

        @Override
        public long getItemId(int index) {
            return innerlListAdapter.getItemId(this.goToIndex(index));
        }

        @Override
        public boolean hasStableIds() {
            return innerlListAdapter.hasStableIds();
        }

        @Override
        public View getView(int index, View convertView, ViewGroup parent) {
            return innerlListAdapter.getView(this.goToIndex(index), convertView, parent);
        }

        @Override
        public int getItemViewType(int index) {
            return innerlListAdapter.getItemViewType(this.goToIndex(index));
        }

        @Override
        public int getViewTypeCount() {
            return innerlListAdapter.getViewTypeCount();
        }

        @Override
        public boolean isEmpty() {
            return innerlListAdapter.isEmpty();
        }

        @Override
        public boolean areAllItemsEnabled() {
            return innerlListAdapter.areAllItemsEnabled();
        }

        @Override
        public boolean isEnabled(int index) {
            return innerlListAdapter.isEnabled(this.goToIndex(index));
        }
    }
}

