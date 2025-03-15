package com.w3itexperts.ombe.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.modals.CoffeeItem;

import java.util.List;

public class CoffeeSlider extends HorizontalScrollView {
    private LinearLayout container;
    private List<CoffeeItem> coffeeItems;
    private int itemWidth;
    private int currentPosition = 0;

    public CoffeeSlider(Context context) {
        super(context);
        init(context);
    }

    public CoffeeSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setHorizontalScrollBarEnabled(false);
        container = new LinearLayout(context);
        container.setOrientation(LinearLayout.HORIZONTAL);
        addView(container);
    }

    public void setCoffeeItems(List<CoffeeItem> items) {
        this.coffeeItems = items;
        container.removeAllViews();
        for (CoffeeItem item : items) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_home_slider_view, container, false);
            ((ImageView) itemView.findViewById(R.id.imageView)).setImageResource(item.getImageResId());
            ((TextView) itemView.findViewById(R.id.titleTextView)).setText(item.getTitle());
            ((TextView) itemView.findViewById(R.id.priceTextView)).setText(item.getPrice());
            container.addView(itemView);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        itemWidth = w;
        for (int i = 0; i < container.getChildCount(); i++) {
            container.getChildAt(i).getLayoutParams().width = itemWidth;
        }
        scrollToPosition(currentPosition);
    }

    public void scrollToPosition(int position) {
        if (position >= 0 && position < coffeeItems.size()) {
            currentPosition = position;
            smoothScrollTo(position * itemWidth, 0);
        }
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                int scrollX = getScrollX();
                int itemIndex = (scrollX + (itemWidth / 2)) / itemWidth;
                scrollToPosition(itemIndex);
                if (listener != null) {
                    listener.onItemSelected(itemIndex);
                }
            }
            return false;
        });
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }
}