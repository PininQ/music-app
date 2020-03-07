package com.demo.musicdemo.helps;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * 三种音乐播放方式
 * 1. 直接在 Activity 中去创建播放音乐，音乐于 Activity 绑定，Activity 运行时播放音乐，Activity 退出时停止播放音乐
 * 2. 通过全局单例类与 Application 绑定，Application 运行时播放音乐，Application 被杀死时停止播放音乐
 * 3. 通过 Service 进行音乐播放，Service 运行时播放音乐，Service 被杀死时停止播放音乐（三种方式中最稳定的播放方式）
 */
public class MediaPlayerHelper {

    private static MediaPlayerHelper instance;

    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private String mPath;
    private OnMediaPlayerHelperListener onMediaPlayerHelperListener;

    public void setOnMediaPlayerHelperListener(OnMediaPlayerHelperListener onMeidaPlayerHelperListener) {
        this.onMediaPlayerHelperListener = onMeidaPlayerHelperListener;
    }

    /**
     * 单例
     *
     * @param context
     * @return
     */
    public static MediaPlayerHelper getInstance(Context context) {

        if (instance == null) {
            synchronized (MediaPlayerHelper.class) {
                if (instance == null) {
                    instance = new MediaPlayerHelper(context);
                }
            }
        }

        return instance;
    }

    public MediaPlayerHelper(Context context) {
        mContext = context;
        mMediaPlayer = new MediaPlayer();

    }

    /**
     * 1. setPath: 当前需要播放的音乐
     * 2. start: 播放音乐
     * 3. pause: 暂停播放
     */

    /**
     * 当前需要播放的音乐
     * @param path
     */
    public void setPath(String path) {
        /**
         * 1. 音乐正在播放，重置音乐播放状态
         * 2. 设置音乐播放路径
         * 3. 准备播放
         */

        /**
         * 【错误逻辑！】当音乐进行切换的时候，如果音乐处于播放状态，那么就去重置音乐的播放状态，
         * 而如果音乐没有处于播放状态的话（暂停），那么就不去重置播放状态
         */
//1. 音乐正在播放，重置音乐播放状态
        if (mMediaPlayer.isPlaying() || !path.equals(mPath)) {
            mMediaPlayer.reset();
        }
        mPath = path;

//2. start: 播放音乐
        try {
            mMediaPlayer.setDataSource(mContext, Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
//3. 准备播放
        mMediaPlayer.prepareAsync();
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (onMediaPlayerHelperListener != null) {
                    onMediaPlayerHelperListener.onPrepared(mp);
                }
            }
        });

//        监听音乐播放完成
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (onMediaPlayerHelperListener != null) {
                    onMediaPlayerHelperListener.onCompletion(mp);
                }
            }
        });

    }

    /**
     * 返回正在播放的音乐路径
     * @return
     */
    public String getPath() {
        return mPath;
    }

    /**
     * 播放音乐
     */
    public void start() {
        if (mMediaPlayer.isPlaying()) return;
        mMediaPlayer.start();
    }

    /**
     * 暂停播放
     */
    public void pause() {
        mMediaPlayer.pause();
    }

    public interface OnMediaPlayerHelperListener {
        void onPrepared(MediaPlayer mp);
        void onCompletion(MediaPlayer mp);
    }


}
