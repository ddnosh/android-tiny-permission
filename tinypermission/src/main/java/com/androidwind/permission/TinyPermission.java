package com.androidwind.permission;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TinyPermission {
    private Activity mActivity;
    private List<String> mPermissions;

    public TinyPermission(Activity activity) {
        mActivity = activity;
    }

    public static TinyPermission start(Activity activity) {
        return new TinyPermission(activity);
    }

    public TinyPermission permission(String... permissions) {
        if (mPermissions == null) {
            mPermissions = new ArrayList<>(permissions.length);
        }
        mPermissions.addAll(Arrays.asList(permissions));
        return this;
    }

    public void request(OnPermission callback) {
        FakeFragment.newInstance((new ArrayList<>(mPermissions))).checkPermission(mActivity, callback);
    }

    public static void gotoPermissionSettings(Context context) {
        PermissionSettingPage.start(context, false);
    }
}
