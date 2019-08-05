package com.androidwind.permission.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                        }else {
                            Toast.makeText(MainActivity.this, "部分权限授予成功，部分权限未正常授予", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean permanent) {
                        if(permanent) {
                            Toast.makeText(MainActivity.this, "被永久拒绝授权，请手动到设置页面授予权限", Toast.LENGTH_SHORT).show();
                            TinyPermission.gotoPermissionSettings(MainActivity.this);
                        } else {
                            Toast.makeText(MainActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
