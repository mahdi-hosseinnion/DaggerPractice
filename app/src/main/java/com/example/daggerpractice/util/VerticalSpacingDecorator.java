package com.example.daggerpractice.util;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpacingDecorator extends RecyclerView.ItemDecoration {
    private final int verticalSpaceHight;

    public VerticalSpacingDecorator(int verticalSpaceHight) {
        this.verticalSpaceHight = verticalSpaceHight;
    }
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.top=verticalSpaceHight;
    }
}
