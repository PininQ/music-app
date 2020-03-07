package com.demo.musicdemo.activitys;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.demo.musicdemo.R;
import com.demo.musicdemo.helps.RealmHelper;
import com.demo.musicdemo.models.MusicModel;
import com.demo.musicdemo.views.PlayMusicView;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class PlayMusicActivity extends BaseActivity {

    public static String MUSIC_ID = "musicId";

    private ImageView mIvBg;
    private TextView mTvName, mTvAuthor;
    private PlayMusicView mPlayMusicView;
    private String mMusicId;
    private RealmHelper mRealmHelper;
    private MusicModel mMusicModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        // 隐藏 statusBar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initData();
        initView();
    }

    private void initData() {
        mMusicId = getIntent().getStringExtra(MUSIC_ID);
        mRealmHelper = new RealmHelper();
        mMusicModel = mRealmHelper.getMusic(mMusicId);
    }

    private void initView() {
        mIvBg = fd(R.id.iv_bg);
        mTvName = fd(R.id.tv_name);
        mTvAuthor = fd(R.id.tv_author);

        // glide-transformations 是配合 glide 使用的类库
        Glide.with(this)
                .load(mMusicModel.getPoster())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 10)))
                .into(mIvBg);

        mTvName.setText(mMusicModel.getName());
        mTvAuthor.setText(mMusicModel.getAuthor());

        mPlayMusicView = fd(R.id.play_music_view);
//        mPlayMusicView.setMusicIcon(mMusicModel.getPoster());
        mPlayMusicView.setMusic(mMusicModel);
        mPlayMusicView.playMusic();
    }

    /**
     * 后退按钮点击事件
     */
    public void onBackClick(View view) {
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayMusicView.destory();
        mRealmHelper.close();
    }
}
