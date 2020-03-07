package com.demo.musicdemo.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

// ImageView 不能直接继承，应该继承 AppCompatImageView
public class WEqualsHImageView extends AppCompatImageView {
    public WEqualsHImageView(Context context) {
        super(context);
    }

    public WEqualsHImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WEqualsHImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 宽高相等
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

//        // 获取 View 宽度
//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        // 获取 View 模式
//        // match_parent、wrap_content、具体的dp 三种模式
//        int mode = MeasureSpec.getMode(widthMeasureSpec);
    }
}
