package com.androidwind.permission;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class FakeFragment extends Fragment {

    private static final String PERMISSIONS = "permissions";
    private OnPermission callback;

    public static FakeFragment newInstance(ArrayList<String> permissions) {
        FakeFragment fragment = new FakeFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(PERMISSIONS, permissions);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<String> permissions = getArguments().getStringArrayList(PERMISSIONS);
        requestPermissions(permissions.toArray(new String[permissions.size() - 1]), (int)Math.random()*100);
    }

    public void checkPermission(Activity activity, OnPermission callback) {
        this.callback = callback;
        activity.getFragmentManager().beginTransaction().add(this, activity.getClass().getName()).commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 获取授予权限
        List<String> succeedPermissions = PermissionUtils.getSucceedPermissions(permissions, grantResults);
        // 如果请求成功的权限集合大小和请求的数组一样大时证明权限已经全部授予
        if (succeedPermissions.size() == permissions.length) {
            // 代表申请的所有的权限都授予了
            callback.hasPermission(succeedPermissions, true);
        } else {

            // 获取拒绝权限
            List<String> failPermissions = PermissionUtils.getFailPermissions(permissions, grantResults);

            // 代表申请的权限中有不同意授予的，如果有某个权限被永久拒绝就返回true给开发人员，让开发者引导用户去设置界面开启权限
            callback.noPermission(failPermissions, PermissionUtils.checkMorePermissionPermanentDenied(getActivity(), failPermissions));

            // 证明还有一部分权限被成功授予，回调成功接口
            if (!succeedPermissions.isEmpty()) {
                callback.hasPermission(succeedPermissions, false);
            }
        }
    }
}
