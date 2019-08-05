package com.androidwind.permission;

import java.util.List;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public interface OnPermission {

    void hasPermission(List<String> granted, boolean isAll);

    void noPermission(List<String> denied, boolean permanent);
}