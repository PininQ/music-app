package com.demo.musicdemo;

import com.blankj.utilcode.util.Utils;
import com.demo.musicdemo.helps.RealmHelper;

import android.app.Application;

import io.realm.Realm;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
        Realm.init(this);

//        数据迁移
        RealmHelper.migration();
    }
}
