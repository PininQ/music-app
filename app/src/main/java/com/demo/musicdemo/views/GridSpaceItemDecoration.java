package com.demo.musicdemo.views;

import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;

    public GridSpaceItemDecoration(int space, RecyclerView parent) {
        mSpace = space;
        getRecyclerViewOffsets(parent);
    }

    /**
     * @param outRect Item 的矩形边界
     * @param view    ItemView
     * @param parent  RecyclerView
     * @param state   RecyclerView 的状态
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.left = mSpace;  // 设置矩形的左偏移量（距离左侧的距离）

        // 判断 Item 是不是每一行的第一个 Item
//        if (parent.getChildLayoutPosition(view) % 3 == 0) {
//            outRect.left = 0;
//        }

    }

    private void getRecyclerViewOffsets(RecyclerView parent) {
//        View margin
//        margin 为正，则 View 会 距离 边界产生一个距离
//        margin 为负，则 View 会 超出 边界产生一个距离
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) parent.getLayoutParams();
        layoutParams.leftMargin = -mSpace;
        parent.setLayoutParams(layoutParams);
    }
}
