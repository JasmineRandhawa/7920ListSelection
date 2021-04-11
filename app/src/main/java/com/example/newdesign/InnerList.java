package com.example.newdesign;

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


/* Inner List View  for cities list*/
public class InnerList extends ListView implements AbsListView.OnScrollListener {

    //class fields
    private int innerListItemHeight = 0;
    private InnerListListener innerListListener;
    private InnerListAdaptor innerListAdaptor;
    private boolean isInfiniteScrollingEnabled = true;
    private double innerListRadius = 0;
    private final int innerListScrollDuration = 50;

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
        return (innerListItemHeight ==0 ? ((getChildAt(0) != null)?(getChildAt(0)).getHeight():innerListItemHeight)
                :innerListItemHeight);
    }

    public void setInnerListAdaptor(ListAdapter listAdapter) {
        innerListAdaptor = new InnerListAdaptor(listAdapter);
        innerListAdaptor.enableInfiniteScroll(isInfiniteScrollingEnabled);
        super.setAdapter(innerListAdaptor);
    }

    public void setInnerListListener(InnerListListener listViewListener) {
        this.innerListListener = listViewListener;
    }

    public void setInfiniteScroll(boolean enableInfiniteScroll) {
        isInfiniteScrollingEnabled = enableInfiniteScroll;
        if (innerListAdaptor != null) {
            innerListAdaptor.enableInfiniteScroll(enableInfiniteScroll);
        }
        if (isInfiniteScrollingEnabled) {
            setVerticalScrollBarEnabled(false);
            setHorizontalScrollBarEnabled(false);
        }
    }
    @TargetApi(Build.VERSION_CODES.FROYO)
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (isInfiniteScrollingEnabled) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (event.getKeyCode()) {
                    case KeyEvent.KEYCODE_DPAD_DOWN:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                            smoothScrollBy(-innerListItemHeight, innerListScrollDuration);
                            return true;
                        }
                        break;
                    case KeyEvent.KEYCODE_DPAD_UP:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                            smoothScrollBy(innerListItemHeight, innerListScrollDuration);
                            return true;
                        }
                        break;
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public int getCenter() {
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) != null && getChildAt(i).getTop() <=  getHeight() / 2.0f
                    && getChildAt(i).getTop() + getChildAt(i).getHeight() >=  getHeight() / 2.0f) {
                    return getFirstVisiblePosition() + i;
            }
        }
        return -1;
    }

    public View getCenterItem() {
        if (getCenter() != -1) {
            return getChildAt(getCenter() - getFirstVisiblePosition() );
        }
        return null;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (!isInTouchMode() && scrollState == SCROLL_STATE_IDLE) {
            scrollSelectedItemToCenter(getCenter());
        }
    }

    @Override
    public void onScroll(AbsListView innerList, int firstItem, int itemDisplayed,int totalItems) {
        if (!isInfiniteScrollingEnabled || this.getChildAt(0) == null || innerListAdaptor.getItemCount() == 0)
            return;

        if (innerListItemHeight == 0)
            innerListItemHeight = this.getChildAt(0).getHeight();

        if (firstItem == 0)
            this.setSelectionFromTop(innerListAdaptor.getItemCount(), this.getChildAt(0).getTop());


            if (totalItems == firstItem + itemDisplayed)
                this.setSelectionFromTop(firstItem - innerListAdaptor.getItemCount(),
                        this.getChildAt(0).getTop());

            if (innerListListener != null) {
                innerListListener.onScrollEnd(this, firstItem, itemDisplayed, totalItems);
            }

            double viewHalfHeight = innerList.getHeight() / 2.0f;
            double yRadius = (innerList.getHeight() + innerListItemHeight) / 2.0f;
            double xRadius = ( innerList.getHeight() < innerList.getWidth()) ? innerList.getHeight() : innerList.getWidth();

            for (int i = 0; i < itemDisplayed; i++) {
                View itemView = this.getChildAt(i);
                if (itemView != null) {
                    double y = Math.abs(viewHalfHeight - (itemView.getTop() + (itemView.getHeight() / 2.0f)));
                    y = Math.min(y, yRadius);
                    double x = (xRadius * Math.cos(Math.asin(y / yRadius)))-xRadius;
                        View temp = itemView;
                        itemView.post(new Runnable() {
                            @Override public void run() {
                                temp.setLayoutParams(new LayoutParams(200, temp.getHeight()));
                                ((TextView)temp).setTextSize(TypedValue.COMPLEX_UNIT_PX,32);
                            }
                        });
                        itemView.setX((int)(itemView.getWidth()-x));
                        itemView.scrollTo((int) (int)(itemView.getWidth()-x)/70, -(int)(itemView.getWidth()-x)/70);
                        itemView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
                        itemView.setBackgroundResource(R.drawable.inner_list_item_design);
                }
            }
    }

    public void scrollFirstItemToCenter() {
        if (!isInfiniteScrollingEnabled)
            return;
        else {
            int topHeight = 0;
            if (getInnerListItemHeight() > 0) {
                topHeight = getHeight() / 2 - getInnerListItemHeight() / 2;
            }
            if (innerListAdaptor.getItemCount() > 0) {
                setSelectionFromTop(innerListAdaptor.getItemCount(), topHeight);
            }
        }
    }

    public void scrollSelectedItemToCenter(int index) {
        if (!isInfiniteScrollingEnabled || innerListAdaptor.getItemCount()==0) {
            return;
        }
        index = index % innerListAdaptor.getItemCount();

        int topHeight =0;
        if (getCenter() % innerListAdaptor.getItemCount() == index && getCenterItem()!=null) {
            topHeight =  getCenterItem().getTop();
        }
        if ( getInnerListItemHeight() > 0) {
            topHeight = getHeight() / 2 -  getInnerListItemHeight()  / 2;}

        setSelectionFromTop(index + innerListAdaptor.getItemCount(), topHeight);
    }

    class InnerListAdaptor implements ListAdapter {
        private boolean infiniteScrolling = true;
        private ListAdapter innerListAdapter;
        public InnerListAdaptor(ListAdapter listAdapter) {
            innerListAdapter = listAdapter;
        }
        private void enableInfiniteScroll(boolean infiniteScroll) {
            infiniteScrolling = infiniteScroll;
        }
        public int getItemCount() {
            return innerListAdapter.getCount();
        }
        public int goToIndex(int position) {
            int count = innerListAdapter.getCount();
            return (count == 0) ? 0 : position % count;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
            innerListAdapter.registerDataSetObserver(observer);
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
            innerListAdapter.unregisterDataSetObserver(observer);
        }

        @Override
        public int getCount() {
            return (infiniteScrolling) ? innerListAdapter.getCount() * 10 : innerListAdapter.getCount();
        }

        @Override
        public Object getItem(int index) {
            return innerListAdapter.getItem(this.goToIndex(index));
        }

        @Override
        public long getItemId(int index) {
            return innerListAdapter.getItemId(this.goToIndex(index));
        }

        @Override
        public boolean hasStableIds() {
            return innerListAdapter.hasStableIds();
        }

        @Override
        public View getView(int index, View convertView, ViewGroup parent) {
            return innerListAdapter.getView(this.goToIndex(index), convertView, parent);
        }

        @Override
        public int getItemViewType(int index) {
            return innerListAdapter.getItemViewType(this.goToIndex(index));
        }

        @Override
        public int getViewTypeCount() {
            return innerListAdapter.getViewTypeCount();
        }


        @Override
        public boolean isEmpty() {
            return innerListAdapter.isEmpty();
        }

        @Override
        public boolean areAllItemsEnabled() {
            return innerListAdapter.areAllItemsEnabled();
        }

        @Override
        public boolean isEnabled(int index) {
            return innerListAdapter.isEnabled(this.goToIndex(index));
        }
    }
}
