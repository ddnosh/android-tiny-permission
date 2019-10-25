package com.androidwind.permission.sample;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidwind.permission.OnPermission;
import com.androidwind.permission.Permission;
import com.androidwind.permission.TinyPermission;

import java.util.List;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void requestPermissionBySelf(View view) {
        int checkSelfPermission;
        String requestPermission = Manifest.permission.READ_EXTERNAL_STORAGE;
        try{
            checkSelfPermission = ActivityCompat.checkSelfPermission(MainActivity.this, requestPermission);
        } catch (RuntimeException e) {
            Log.e("MainActivity", "RuntimeException:" + e.getMessage());
            Toast.makeText(this, "获取权限失败！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, requestPermission)) {
                shouldShowRationale(this, 1, requestPermission);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        } else {
            Toast.makeText(this, "已获得权限！", Toast.LENGTH_SHORT).show();
        }
    }

    private static void shouldShowRationale(final Activity activity, final int requestCode, final String requestPermission) {
        Toast.makeText(activity, "文案：向用户解释申请这个权限的必要性，要求用户开启这个权限。", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

    }


    public void requestPermission(View view) {
        TinyPermission.start(this)
                .permission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
                .permission(Permission.RECORD_AUDIO)
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            Toast.makeText(MainActivity.this, "授予所有权限成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "部分权限授予成功，部分权限未正常授予", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean permanent) {
                        if (permanent) {
                            Toast.makeText(MainActivity.this, "被永久拒绝授权，请手动到设置页面授予权限", Toast.LENGTH_SHORT).show();
                            TinyPermission.gotoPermissionSettings(MainActivity.this);
                        } else {
                            Toast.makeText(MainActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
