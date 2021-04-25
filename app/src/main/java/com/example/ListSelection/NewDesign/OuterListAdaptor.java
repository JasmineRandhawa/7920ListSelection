package com.example.ListSelection.NewDesign;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.ListSelection.R;

import java.util.ArrayList;
import java.util.List;

public class OuterListAdaptor<Integer> extends CitiesListView.Adapter<CitiesListView.ListItemView> {

    // class fields
    private final Context context;
    private final LayoutInflater layoutInflater;
    private final List<Integer> outerListData;
    private final CitiesListView citiesListView;
    private final int mDuration = 400;
    InnerList innerList;
    int noOfTaps = 0 ;
    String selectedCity = "";
    TextView selectedCityTextView;
    boolean isFirstTimeTouched = true;
    private AnimatorSet animatorSet;

    //constructor
    public OuterListAdaptor(Context ctx, CitiesListView citiesListView, TextView view) {
        this.context = ctx;
        this.layoutInflater = LayoutInflater.from(context);
        this.outerListData = new ArrayList();
        this. citiesListView = citiesListView;
        selectedCityTextView = view;
        selectedCity="";
        selectedCityTextView.setText("");
        isFirstTimeTouched= true;
    }

    public CitiesListView.ListItemView onCreateView(ViewGroup parent, int viewType) {
        return new OuterListContainer(getLayoutInflater().inflate(R.layout.outer_list_item, parent, false));
    }

    //get set methods
    public void setOuterListData(List<Integer> outerListItemData) {
        this.outerListData.clear();
        if (outerListItemData != null) {
            this.outerListData.addAll(outerListItemData);
        }
        this.notifyDataSetChanged();
    }

    public int getItemViewType(int position) {
        return R.layout.outer_list_item;
    }

    public LayoutInflater getLayoutInflater() {
        return this.layoutInflater;
    }

    public Integer getItem(int index) {
        return this.outerListData.get(index);
    }

    public int getItemCount() {
        return outerListData.size();
    }

    protected int getBottomItemCount(int collapseShowItemCount) {
        return citiesListView.getOverlapGapsCollapse()
                * (citiesListView.getNumBottomShow() - collapseShowItemCount - (citiesListView.getNumBottomShow() -
                (citiesListView.getChildCount() - citiesListView.getSelectedIndex() > citiesListView.getNumBottomShow()
                        ? citiesListView.getNumBottomShow()
                        : citiesListView.getChildCount() - citiesListView.getSelectedIndex() - 1)));
    }

    public int getDuration() {
        return 200;
    }

    //helper methods
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void bindView(Integer outerListItemData, int pos, CitiesListView.ListItemView container) {
        OuterListContainer olc = (OuterListContainer) container;
        olc.onBind(outerListItemData, pos);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onBindItemContainer(CitiesListView.ListItemView holder, int position) {
        Integer data = this.getItem(position);
        bindView(data, position, holder);
    }

    void refreshCircular(InnerList list) {
        TextView listSelectedItem = (TextView) list.getCenterItem();
        for (int i = 0; i < list.getChildCount(); i++) {
            TextView listItem = (TextView) list.getChildAt(i);
            listItem.setTextColor(context.getResources().getColor(R.color.DarkBlue));
            if (listItem != null && listItem != listSelectedItem) {
                listItem.setBackgroundResource(R.drawable.inner_list_item_design);
            }
        }
        if (listSelectedItem != null) {
            listSelectedItem.setBackgroundResource(R.drawable.inner_list_selected_item_shape);
            listSelectedItem.setTextColor(context.getResources().getColor(R.color.Red));
            selectedCity = (String) listSelectedItem.getText();
            if(isFirstTimeTouched) {
                selectedCityTextView.setText("");
                isFirstTimeTouched= false;
            }
            else{
                if (selectedCityTextView != null)
                    selectedCityTextView.setText(selectedCity);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void SetUpInnerList(String title) {
        noOfTaps = 0;
        innerList.setX(-200);
        innerList.setY(+110);
        innerList.setInnerListAdapter(Cities.GetListAdaptor(context, title));
        innerList.setInnerListAlignment(InnerList.InnerListListener.ItemAllignment.Left);
        innerList.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_UP)
                noOfTaps++;
            return false;
        });
        innerList.setInnerListListener((filteredCitiesList, firstItem, displayedItems, totalItems)
                -> {refreshCircular(filteredCitiesList);});
    }
    public int getNoOfTaps() {return noOfTaps; }

    public void setNoOfTaps(int nt) { noOfTaps = nt; }


    public void itemClick(final CitiesListView.ListItemView listContainer, int position) {
        noOfTaps++;
        selectedCityTextView.setText("");
        selectedCity="";
        if (animatorSet != null && animatorSet.isRunning()) return;
        initAnimatorSet();
        if (citiesListView.getSelectedIndex() == position) {
            collapse(listContainer);
        } else {
            expand(listContainer, position);
        }
        if (citiesListView.getChildCount() == 1)
            animatorSet.end();
    }

    //animation methods
    private void initAnimatorSet() {
        animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.setDuration(getDuration());
    }

    private void expand(final CitiesListView.ListItemView viewHolder, int position) {
        final int preSelectPosition = citiesListView.getSelectedIndex();
        final CitiesListView.ListItemView preSelectViewHolder = citiesListView.getListContainer(preSelectPosition);
        if (preSelectViewHolder != null) {
            preSelectViewHolder.onOuterItemExpansion(false);
        }
        citiesListView.setSelectedIndex(position);
        itemExpansion(viewHolder);
        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                citiesListView.setScrollEnable(false);
                if (preSelectViewHolder != null) {
                    preSelectViewHolder.onAnimationRepeat(false, 0);
                }
                viewHolder.onAnimationRepeat(true, 0);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                viewHolder.onOuterItemExpansion(true);
                if (preSelectViewHolder != null) {
                    preSelectViewHolder.onAnimationRepeat(false, 1);
                }
                viewHolder.onAnimationRepeat(true, 1);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                if (preSelectViewHolder != null) {
                    preSelectViewHolder.onAnimationRepeat(false, 2);
                }
                viewHolder.onAnimationRepeat(true, 2);
            }
        });
        animatorSet.start();
    }

    private void collapse(final CitiesListView.ListItemView viewHolder) {
        itemCollapse();
        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                viewHolder.onOuterItemExpansion(false);
                citiesListView.setScrollEnable(true);
                viewHolder.onAnimationRepeat(false, 0);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                citiesListView.setSelectedIndex(-1);
                viewHolder.onAnimationRepeat(false, 1);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                viewHolder.onAnimationRepeat(false, 2);
            }
        });
        animatorSet.start();
    }

    private void itemExpansion(final CitiesListView.ListItemView listContainer) {
        final View itemView = listContainer.itemView;
        itemView.clearAnimation();
        ObjectAnimator oa = ObjectAnimator.ofFloat(itemView, View.Y, itemView.getY(), citiesListView.getScrollY()
                + citiesListView.getPaddingTop());
        animatorSet.play(oa);
        int collapseShowItemCount = 0;
        for (int i = 0; i < citiesListView.getChildCount(); i++) {
            int childTop;
            if (i == citiesListView.getSelectedIndex()) continue;
            final View child = citiesListView.getChildAt(i);
            child.clearAnimation();
            if (i > citiesListView.getSelectedIndex() && collapseShowItemCount < citiesListView.getNumBottomShow()) {
                childTop = citiesListView.getShowHeight() - getBottomItemCount(collapseShowItemCount) + citiesListView.getScrollY();
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), childTop);
                animatorSet.play(oAnim);
                collapseShowItemCount++;
            } else {
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), citiesListView.getShowHeight() +
                        citiesListView.getScrollY());
                animatorSet.play(oAnim);
            }
        }
    }

    private void itemCollapse() {
        int childTop = citiesListView.getPaddingTop();
        for (int i = 0; i < citiesListView.getChildCount(); i++) {
            View child = citiesListView.getChildAt(i);
            child.clearAnimation();
            final CitiesListView.LayoutParams lp =
                    (CitiesListView.LayoutParams) child.getLayoutParams();
            childTop += lp.topMargin;
            if (i != 0) {
                childTop -= citiesListView.getOverlapGaps() * 2;
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), childTop);
                animatorSet.play(oAnim);
            } else {
                ObjectAnimator oAnim = ObjectAnimator.ofFloat(child, View.Y, child.getY(), childTop);
                animatorSet.play(oAnim);
            }
            childTop += lp.mHeaderHeight;
        }
    }

    //Outer Item Class
    public class OuterListContainer extends CitiesListView.ListItemView {
        View listItemLayout;
        View listItemData;

        TextView listItemTitle;

        public OuterListContainer(View view) {
            super(view);
            listItemLayout = view.findViewById(R.id.list_item_layout);
            listItemData = view.findViewById(R.id.inner_list_item);
            innerList = view.findViewById(R.id.cityList);
            listItemTitle = (TextView) view.findViewById(R.id.list_item_title);
        }

        @Override
        public void onOuterItemExpansion(boolean isExpand) {
            listItemData.setVisibility(isExpand ? View.VISIBLE : View.GONE);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void onBind(Integer data, int position) {
            listItemLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), (java.lang.Integer) data), PorterDuff.Mode.SRC_IN);
            String title = OuterList.GetData()[position];
            listItemTitle.setText(title);
            SetUpInnerList(title);
        }
    }
}