# android-tiny-permission
[![Download](https://api.bintray.com/packages/ddnosh/maven/tinypermission/images/download.svg) ](https://bintray.com/ddnosh/maven/tinypermission/_latestVersion)  
a tiny permission library for android.

# Solution
1. use an agent fragment(requestPermissions) to request permission;
2. the caller handle the callback from the fragment;
3. no CompatSupport library;
4. chain way to use;

# Function
1. .permission(...) with the permissions you want;
2. OnPermission: you handle the callback from the permissions dialog;

# Usage
``` 
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
```
