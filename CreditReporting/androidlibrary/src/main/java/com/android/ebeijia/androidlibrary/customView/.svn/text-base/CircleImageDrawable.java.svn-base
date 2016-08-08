package com.android.ebeijia.androidlibrary.customView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

/**
 * Created by wuqinghai on 16/2/21.
 */
public class CircleImageDrawable extends Drawable {
    private int mWidth;
    private Paint mPaint;


    public CircleImageDrawable(Bitmap bitmap){
        mWidth= Math.min(bitmap.getWidth(), bitmap.getHeight());
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        BitmapShader shader=new BitmapShader(bitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        mPaint.setShader(shader);

    }


    @Override
    public void draw(Canvas canvas) {
       canvas.drawCircle(mWidth/2,mWidth/2,mWidth/2,mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
       mPaint.setAlpha(alpha);
    }

    @Override
    public int getIntrinsicHeight() {
        return mWidth;
    }

    @Override
    public int getIntrinsicWidth() {
        return mWidth;
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
       mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
