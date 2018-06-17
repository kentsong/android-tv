package com.example.kent.tv_view_focus.feature.grid_recyclerview;

import android.support.annotation.IntDef;

/**
 * Created by KentSong on 2018/6/12.
 */
public interface FocusableContract {

    /**
     * 布局模式
     */
    int LINEARLAYOUT_HORIZONTAL = 1;
    int LINEARLAYOUT_VERTICAL = 2;
    int GRIDLAYOUT_HORIZONTAL = 3;
    int GRIDLAYOUT_VERTICAL = 4;

    @IntDef({LINEARLAYOUT_HORIZONTAL, LINEARLAYOUT_VERTICAL, GRIDLAYOUT_HORIZONTAL, GRIDLAYOUT_VERTICAL})
    @interface LayoutMode {
    }

}
