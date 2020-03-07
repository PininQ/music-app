package com.demo.musicdemo.activitys;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.demo.musicdemo.R;

public class BaseActivity extends Activity {
    private ImageView mIvBack, mIvMe;
    private TextView mTvTitle;

    /**
     * findViewById
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T fd(@IdRes int id) {
        return findViewById(id);
    }

    /**
     * 初始化 NavigationBar
     *
     * @param isShowBack
     * @param title
     * @param isShowMe
     */
    protected void initNavBar(boolean isShowBack, String title, boolean isShowMe) {
        mIvBack = fd(R.id.iv_back);
        mTvTitle = fd(R.id.tv_title);
        mIvMe = fd(R.id.iv_me);

        mIvBack.setVisibility(isShowBack ? View.VISIBLE : View.GONE);
        mIvMe.setVisibility(isShowMe ? View.VISIBLE : View.GONE);
        mTvTitle.setText(title);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // 后退
            }
        });

        mIvMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.this, MeActivity.class);
                startActivity(intent);
            }
        });
    }
}
