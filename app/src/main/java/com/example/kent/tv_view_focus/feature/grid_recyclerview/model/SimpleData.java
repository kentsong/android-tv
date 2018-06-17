package com.example.kent.tv_view_focus.feature.grid_recyclerview.model;

/**
 * Created by KentSong on 2018/6/11.
 * 简易资料物件 (可扩展、勿修改)
 */
public class SimpleData {

    //字段一
    private String str_1;
    //字段二
    private String str_2;
    //字段三
    private String str_3;

    private boolean isFocused;
    private boolean isSelected;

    public SimpleData(String str_1) {
        this.str_1 = str_1;
    }

    public SimpleData(String str_1, String str_2) {
        this.str_1 = str_1;
        this.str_2 = str_2;
    }

    public String getStr_1() {
        return str_1;
    }

    public void setStr_1(String str_1) {
        this.str_1 = str_1;
    }

    public String getStr_2() {
        return str_2;
    }

    public void setStr_2(String str_2) {
        this.str_2 = str_2;
    }

    public String getStr_3() {
        return str_3;
    }

    public void setStr_3(String str_3) {
        this.str_3 = str_3;
    }

    public boolean isFocused() {
        return isFocused;
    }

    public void setFocused(boolean focused) {
        isFocused = focused;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
