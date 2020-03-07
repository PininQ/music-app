package com.demo.musicdemo.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;

import com.demo.musicdemo.R;
import com.demo.musicdemo.activitys.WelcomeActivity;
import com.demo.musicdemo.helps.MediaPlayerHelper;
import com.demo.musicdemo.models.MusicModel;

/**
 * 1. 通过 Service 连接 PlayMusicView 和 MediaPlayHelper
 * 2. PlayMusicView -- Service：
 * 1. 播放音乐、暂停音乐
 * 2. 启动Service、绑定Service、解除绑定Service
 * 3. MediaPlayHelper -- Service
 * 1. 播放音乐、暂停音乐
 * 2. 监听音乐播放完成，停止 Service
 */
public class MusicService extends Service {

    //    notification id不可设置为 0
    public static final int NOTIFICATION_ID = 12666;

    private MediaPlayerHelper mMediaPlayerHelper;
    private MusicModel mMusicModel;

    public MusicService() {
    }

    public class MusicBind extends Binder {
        /**
         * 设置音乐（MusicModel）
         */
        public void setMusic(MusicModel musicModel) {
            mMusicModel = musicModel;
            startForeground();
        }

        /**
         * 播放音乐
         */
        public void playMusic() {

            /**
             * 1. 判断当前音乐是否已经在播放的音乐
             * 2. 如果当前的音乐是已经在播放的音乐的话，那么就直接执行 start 方法
             * 3. 如果当前播放的音乐不是需要播放的音乐的话，那么就调用 setPath 的方法
             */
            if (mMediaPlayerHelper.getPath() != null
                    && mMediaPlayerHelper.getPath().equals(mMusicModel.getPath())) {
                mMediaPlayerHelper.start();
            } else {
                mMediaPlayerHelper.setPath(mMusicModel.getPath());
                mMediaPlayerHelper.setOnMediaPlayerHelperListener(new MediaPlayerHelper.OnMediaPlayerHelperListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        // 监听当前准备播放音乐是否已经完成，准备完成了就播放音乐
                        mMediaPlayerHelper.start();
                    }

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopSelf();
                    }
                });
            }
        }

        /**
         * 暂停音乐
         */
        public void stopMusic() {
            mMediaPlayerHelper.pause();
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBind();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mMediaPlayerHelper = MediaPlayerHelper.getInstance(this);
    }

    /**
     * 系统默认不允许不可见的后台服务播放音乐，
     * Notification 可以在系统通知栏可见
     */
    /**
     * 设置服务在前台可见
     */
    private void startForeground() {

//        通知栏点击跳转的 Intent
        PendingIntent pendingIntent = PendingIntent
                .getActivity(this, 0, new Intent(this, WelcomeActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);

//        创建 Notification
        Notification notification = null;

        /**
         * android API 26 以上 NotificationChannel 特性适配
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = createNotificationChannel();
            notification = new Notification.Builder(this, channel.getId())
                    .setContentTitle(mMusicModel.getName())
                    .setContentText(mMusicModel.getAuthor())
                    .setSmallIcon(R.mipmap.logo)
                    .setContentIntent(pendingIntent)
                    .build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        } else {
            notification = new Notification.Builder(this)
                    .setContentTitle(mMusicModel.getName())
                    .setContentText(mMusicModel.getAuthor())
                    .setSmallIcon(R.mipmap.logo)
                    .setContentIntent(pendingIntent)
                    .build();
        }

//        设置 Notification 在前台显示
        startForeground(NOTIFICATION_ID, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel createNotificationChannel() {
        String channelId = "shanshan";
        String channelName = "ShanshanMusicService";
        String Description = "ShanshanMusic";
        NotificationChannel channel = new NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(Description);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        channel.setShowBadge(false);

        return channel;
    }
}
