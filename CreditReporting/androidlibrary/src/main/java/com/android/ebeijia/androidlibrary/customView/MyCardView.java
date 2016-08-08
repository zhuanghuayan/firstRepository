package com.android.ebeijia.androidlibrary.customView;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.android.ebeijia.androidlibrary.utils.ViewsUtil;

/**
 * Created by wuqinghai on 16/4/6.
 */
public class MyCardView extends CardView{

    public MyCardView(Context context) {
        this(context, null);
    }

    public MyCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }


}
