package com.w3itexperts.ombe.methods;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OffsetItemDecoration extends RecyclerView.ItemDecoration {
    private final int pageMargin;
    private final int offset;

    public OffsetItemDecoration(int pageMargin, int offset) {
        this.pageMargin = pageMargin;
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int itemCount = state.getItemCount();

        if (position == 0) {
            // Apply left margin only for the first item
            outRect.left = offset;
        } else if (position == itemCount - 1) {
            // Apply right margin only for the last item
            outRect.right = offset;
        } else {
            // Apply margin to both sides
            outRect.left = pageMargin / 2;
            outRect.right = pageMargin / 2;
        }
    }
}
