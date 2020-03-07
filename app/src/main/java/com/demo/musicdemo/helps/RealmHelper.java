package com.demo.musicdemo.helps;

import android.content.Context;

import com.demo.musicdemo.migration.Migration;
import com.demo.musicdemo.models.AlbumModel;
import com.demo.musicdemo.models.MusicModel;
import com.demo.musicdemo.models.MusicSourceModel;
import com.demo.musicdemo.models.UserModel;
import com.demo.musicdemo.utils.DataUtils;

import java.io.FileNotFoundException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmHelper {

    private Realm mRealm;

    public RealmHelper() {
        mRealm = Realm.getDefaultInstance();
    }

    /**
     * 关闭数据库
     */
    public void close() {
        if (mRealm != null && !mRealm.isClosed()) {
            mRealm.close();
        }
    }

    /**
     * Realm 数据库发生结构性变化（模型或者模型中的字段发生了新增、修改删除的时候，我们就需要对数据库进行迁移）
     */

    /**
     * 告诉 Realm 数据需要迁移，并且为 Realm 设置最新的配置
     */
    public static void migration() {
        RealmConfiguration conf = getRealmConf();

//        为 Realm 设置最新的配置
        Realm.setDefaultConfiguration(conf);
//        告诉 Realm 数据库需要迁移
        try {
            Realm.migrateRealm(conf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回 RealmConfiguration
     */
    private static RealmConfiguration getRealmConf() {
        return new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new Migration())
                .build();
    }

    /**
     * 保存用户信息
     */
    public void saveUser(UserModel userModel) {
        mRealm.beginTransaction();
        mRealm.insert(userModel);  // 注册同样的手机号会退出程序   原因为手机号作为主键并且已经存在
//        mRealm.insertOrUpdate(userModel);
        mRealm.commitTransaction();
    }

    /**
     * 返回所有用户
     */
    public List<UserModel> getAllUser() {
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        RealmResults<UserModel> results = query.findAll();
        return results;
    }

    /**
     * 验证用户信息
     */
    public boolean validateUser(String phone, String password) {
        boolean result = false;
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        query = query.equalTo("phone", phone).equalTo("password", password);
        UserModel userModel = query.findFirst();

        if (userModel != null) {
            result = true;
        }
        return result;
    }

    /**
     * 获取当前用户
     */
    public UserModel getUser() {
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        UserModel userModel = query.equalTo("phone", UserHelper.getInstance().getPhone()).findFirst();
        return userModel;
    }

    /**
     * 修改密码
     */
    public void changePassword(String password) {
        UserModel userModel = getUser();
        mRealm.beginTransaction();
        userModel.setPassword(password);
        mRealm.commitTransaction();
    }

    /**
     * 1. 用户登录，存放数据
     * 2. 用户退出，删除数据
     */

    /**
     * 保存音乐源数据
     */
    public void setMusicSource(Context context) {
//        拿到资源文件中的数据
        String musicSourceJson = DataUtils.getJsonFromAssets(context, "DataSource.json");
        mRealm.beginTransaction();
        mRealm.createObjectFromJson(MusicSourceModel.class, musicSourceJson);
        mRealm.commitTransaction();
    }

    /**
     * 删除音乐源数据
     * 1. RealmResult delete
     * 2. Realm delete 删除这个模型下所有的数据
     */
    public void removeMusicSource() {
        mRealm.beginTransaction();
        mRealm.delete(MusicSourceModel.class);
        mRealm.delete(AlbumModel.class);
        mRealm.delete(MusicModel.class);
        mRealm.commitTransaction();
    }

    /**
     * 返回音乐源数据
     */
    public MusicSourceModel getMusicSource() {
        return mRealm.where(MusicSourceModel.class).findFirst();
    }

    /**
     * 返回歌单
     */
    public AlbumModel getAlbum(String albumId) {
        return mRealm.where(AlbumModel.class).equalTo("albumId", albumId).findFirst();
    }

    /**
     * 返回音乐
     */
    public MusicModel getMusic(String musicId) {
        return mRealm.where(MusicModel.class).equalTo("musicId", musicId).findFirst();
    }
}
