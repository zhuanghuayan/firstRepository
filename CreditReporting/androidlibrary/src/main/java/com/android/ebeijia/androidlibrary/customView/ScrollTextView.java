package com.android.ebeijia.androidlibrary.customView;

import android.content.Context;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by wuqinghai on 16/4/29.
 */
public class ScrollTextView extends TextView{
    public ScrollTextView(Context context) {
        this(context, null);
    }

    public ScrollTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //ClickableSpan
        setMovementMethod(ScrollingMovementMethod.getInstance());
    }


}
