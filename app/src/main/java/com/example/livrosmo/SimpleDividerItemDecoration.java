package com.example.livrosmo;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
    private final Paint paint = new Paint();

    public SimpleDividerItemDecoration(int color, int height) {
        paint.setColor(color);
        paint.setStrokeWidth(height);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        // Add space for the divider at the bottom of each item
        outRect.bottom = (int) paint.getStrokeWidth();
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        // Draw dividers below each item
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int top = child.getBottom();
            int bottom = top + (int) paint.getStrokeWidth();
            c.drawRect(left, top, right, bottom, paint);
        }
    }
}
