package com.demo.musicdemo.helps;

/**
 * 用户自动登录逻辑
 *
 * 1. 用户登录
 *      1. 当用户登录时，利用 SharedPreferences 保存登录用户的用户标记（用户手机号码）
 *      2. 利用全局单例类 UserHelper 保存登录用户信息
 *          1. 用户登录之后
 *          2. 用户打开应用程序，检测 SharedPreferences 是否存在用户登录标记，如果存在则为 UserHelper 进行赋值，
 *              并进入主页。如果不存在，则进入登录页面。
 * 2. 用户退出
 *      1. 删除 SharedPreferences 中保存的用户标记，退出到登录页面。
 */
public class UserHelper {

    private static UserHelper instance;

    public UserHelper() {

    }

    /**
     * UserHelper 全局单例模式
     * @return
     */
    public static UserHelper getInstance() {
        if (instance == null) {
            synchronized (UserHelper.class) {
                if (instance == null) {
                    instance = new UserHelper();
                }
            }
        }
        return instance;
    }

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
