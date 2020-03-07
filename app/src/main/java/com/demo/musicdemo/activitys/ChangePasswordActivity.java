package com.demo.musicdemo.activitys;

import android.os.Bundle;
import android.view.View;

import com.demo.musicdemo.R;
import com.demo.musicdemo.utils.UserUtils;
import com.demo.musicdemo.views.InputView;

public class ChangePasswordActivity extends BaseActivity {

    private InputView mOldPassword, mNewPassword, mConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initView();
    }

    private void initView() {
        initNavBar(true, "修改密码", false);

        mOldPassword = fd(R.id.input_old_password);
        mNewPassword = fd(R.id.input_new_password);
        mConfirmPassword = fd(R.id.input_confirm_password);
    }

    public void onChangePasswordClick(View v) {
        String oldPassword = mOldPassword.getInputStr();
        String newPassword = mNewPassword.getInputStr();
        String confirmPassword = mConfirmPassword.getInputStr();

        boolean result = UserUtils.changePassword(this, oldPassword, newPassword, confirmPassword);
        if (!result) {
            return;
        }

        UserUtils.logout(this);
    }
}
