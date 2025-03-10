package com.w3itexperts.ombe.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

public class SwipeToRevealCallback extends ItemTouchHelper.SimpleCallback {

    private SwipeableAdapter adapter;
    private Context context;
    boolean right, left;

    public SwipeToRevealCallback(SwipeableAdapter adapter, Context context) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
        this.context = context;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;  // We don't need to handle move actions
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // Remove the item from the adapter's data list
        int position = viewHolder.getAdapterPosition();
        adapter.removeItem(position);

    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {

        View itemView = ((SwipeableAdapter.SwipeViewHolder) viewHolder).itemView; // Main content view
        View deleteLayout = ((SwipeableAdapter.SwipeViewHolder) viewHolder).deleteLayout;
        View editRemoveLayout = ((SwipeableAdapter.SwipeViewHolder) viewHolder).editRemoveLayout;

        float width = itemView.getWidth();
        float halfWidth = width / 2;  // Set breakpoint at half width of item

        // Swiping right to reveal edit/remove buttons
        if (dX > 0) {
            float translationX = Math.min(dX, halfWidth);  // Limit swipe to half width
            itemView.setTranslationX(translationX);  // Set translation to the limited value

            if (dX >= 200) {
                setStartRightMargin(itemView, 20, 350, 20, 20, 20, 20, 20, 20);
                editRemoveLayout.setVisibility(View.VISIBLE);  // Ensure the edit/remove buttons are visible
                deleteLayout.setVisibility(View.GONE);

            } else {
                if (editRemoveLayout.getVisibility() != View.VISIBLE) {
                    setStartRightMargin(itemView, 20, 20, 20, 20, 20, 20, 20, 20);
                    deleteLayout.setVisibility(View.GONE);
                    editRemoveLayout.setVisibility(View.GONE);
                }
            }
        }
        // Swiping left to reveal delete button
        else if (dX < 0) {
            float translationX = Math.max(dX, -halfWidth);  // Limit swipe to half width
            itemView.setTranslationX(translationX);  // Set translation to the limited value

            if (dX <= -180) {
                setStartRightMargin(itemView, 20, 20, 20, 20, 2, 350, 20, 20);
                deleteLayout.setVisibility(View.VISIBLE);
                editRemoveLayout.setVisibility(View.GONE);
            } else {
                if (deleteLayout.getVisibility() != View.VISIBLE) {
                    setStartRightMargin(itemView, 20, 20, 20, 20, 20, 20, 20, 20);
                    editRemoveLayout.setVisibility(View.GONE);
                    deleteLayout.setVisibility(View.GONE);
                }
            }

        }
        // Swiping to reveal all button
        else {
            itemView.setTranslationX(0);
//        deleteLayout.setVisibility(View.GONE);  // Hide delete button
//        editRemoveLayout.setVisibility(View.GONE);  // Hide edit/remove buttons
        }



        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    // Method to animate the translation (margins) of a view
    private void setStartRightMargin(View view, int startLeftMargin, int endLeftMargin, int startTopMargin, int endTopMargin,
                                     int startRightMargin, int endRightMargin, int startBottomMargin, int endBottomMargin) {
        ValueAnimator marginAnimator = ValueAnimator.ofFloat(0f, 1f);
        marginAnimator.setDuration(300);  // Duration in milliseconds
        marginAnimator.addUpdateListener(animation -> {
            float fraction = animation.getAnimatedFraction();

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();

            params.leftMargin = (int) (startLeftMargin + fraction * (endLeftMargin - startLeftMargin));
            params.topMargin = (int) (startTopMargin + fraction * (endTopMargin - startTopMargin));
            params.rightMargin = (int) (startRightMargin + fraction * (endRightMargin - startRightMargin));
            params.bottomMargin = (int) (startBottomMargin + fraction * (endBottomMargin - startBottomMargin));
            view.setLayoutParams(params);
        });
        marginAnimator.start();
    }


}
