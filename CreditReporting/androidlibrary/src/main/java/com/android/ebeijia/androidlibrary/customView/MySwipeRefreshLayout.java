package com.android.ebeijia.androidlibrary.customView;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * Created by wuqinghai on 16/1/17.
 */
public class MySwipeRefreshLayout extends SwipeRefreshLayout {
    public boolean isRequesting() {
        return isRequesting;
    }

    public void setRequesting(boolean isRequesting) {
        if(isRefreshing()&&isRequesting){
            setRefreshing(false);
        }
        setRefreshing(isRequesting);
        this.isRequesting = isRequesting;
    }
    public void setRequesting2(boolean isRequesting) {
        setRefreshing(isRequesting);
        this.isRequesting = isRequesting;
    }


    private boolean isRequesting=false;
    public MySwipeRefreshLayout(Context context) {
        super(context);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
