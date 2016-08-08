package com.android.ebeijia.androidlibrary.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.ebeijia.androidlibrary.annotation.ViewInject;
import com.android.ebeijia.androidlibrary.application.BaseApplication;

import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class RootActivity extends AppCompatActivity {
    protected InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setWindowStatusAndNavigationTranslucent();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        initKeyBroadListener();


    }
    @TargetApi(19)
    public void setWindowStatusAndNavigationTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            /**
             * 下面两句话需要配合style.xml <item name="android:fitsSystemWindows">true</item> 使用
             * 如果是<item name="android:fitsSystemWindows">false</item> 我们应用的布局会在手机的顶部
             * 如果<item name="android:fitsSystemWindows">true</item> 我们的应用布局会在状态栏下面
             * 不过上面的情况是状态是overlay在我们的布局之上的。true也只是加了paddingTop=状态栏的高度
             * 如果要更改状态栏的颜色请参考 https://github.com/jgilfelt/SystemBarTint
             */
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//状态栏颜色透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//有的手机底部有虚拟导航栏
        }
    }

    /**
     * 在setContentView之后调用
     */
    public void showStatusBar(){
        int version = android.os.Build.VERSION.SDK_INT;
        if (version < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            // 小于16版本，那么直接退出
            return;
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    /**
     * 在setContentView之后调用
     * 这个方法会隐藏状态栏，但是可能还会隐藏其他的布局，比如在AppCampatActivity中如果设置了overlay 的actionBar也会跟着隐藏
     */
    public void hideStatusBar(){
        int version = android.os.Build.VERSION.SDK_INT;
        if (version < Build.VERSION_CODES.JELLY_BEAN) {

            return;
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
    private void initKeyBroadListener() {


        mIsSoftKeyboardShowing = false;
        mKeyboardStateListeners = new ArrayList<OnSoftKeyboardStateChangedListener>();
        mLayoutChangeListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //判断窗口可见区域大小
                Rect r = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //如果屏幕高度和Window可见区域高度差值大于整个屏幕高度的1/3，则表示软键盘显示中，否则软键盘为隐藏状态。- r.top
                int heightDifference = getResources().getDisplayMetrics().heightPixels - r.bottom;//这里是相对于手机屏幕左上角的位置的底部坐标
                boolean isKeyboardShowing = heightDifference > getResources().getDisplayMetrics().heightPixels/3;

                //如果之前软键盘状态为显示，现在为关闭，或者之前为关闭，现在为显示，则表示软键盘的状态发生了改变
                if ((mIsSoftKeyboardShowing && !isKeyboardShowing) || (!mIsSoftKeyboardShowing && isKeyboardShowing)) {
                    mIsSoftKeyboardShowing = isKeyboardShowing;
                    for (int i = 0; i < mKeyboardStateListeners.size(); i++) {
                        OnSoftKeyboardStateChangedListener listener = mKeyboardStateListeners.get(i);
                        listener.OnSoftKeyboardStateChanged(mIsSoftKeyboardShowing, heightDifference);
                    }
                }
            }
        };
        //注册布局变化监听
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mLayoutChangeListener);
    }

    protected void autoInjectAnnotationField() {
        Class clazz = getClass();
        //  Field [] s=clazz.getFields();
        Field[] fields = clazz.getDeclaredFields();

        for (Field f : fields) {
            if (f.isAnnotationPresent(ViewInject.class)) {
                ViewInject viewInject = f.getAnnotation(ViewInject.class);
                int id = viewInject.value();
                if (id > 0) {
                    try {
                        f.setAccessible(true);
                        f.set(this, this.findViewById(id));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (this.getCurrentFocus() != null) {
                if (this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(this.getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void finish() {
        hintKb();
        super.finish();
    }

    @Override
    protected void onDestroy() {
        hintKb();
        removeKeyBroadListener();
        BaseApplication.bContext.isExit=false;
        super.onDestroy();
    }

    private void removeKeyBroadListener() {
        //移除布局变化监听
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(mLayoutChangeListener);
        } else {
            getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(mLayoutChangeListener);
        }

    }

    public void hintKb() {

        if (imm.isActive()) {
            if (this.getCurrentFocus() != null) {
                if (this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(this.getCurrentFocus()
                                    .getWindowToken(),
                            0);
                }
            }

        }
    }

    @SuppressLint("NewApi")
    public AlertDialog.Builder createAlertBuild() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= 14) {
            builder = new AlertDialog.Builder(this,
                    AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        return builder;
    }

    public void goToBrowser(String url) {
        try {
            Uri uri = Uri.parse(url); // 浏览器
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(it);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"跳转地址无效",Toast.LENGTH_SHORT).show();
        }

    }

    public interface OnSoftKeyboardStateChangedListener {
        public void OnSoftKeyboardStateChanged(boolean isKeyBoardShow, int keyboardHeight);
    }

    //注册软键盘状态变化监听
    public void addSoftKeyboardChangedListener(OnSoftKeyboardStateChangedListener listener) {
        if (listener != null) {
            mKeyboardStateListeners.add(listener);
        }
    }
    //取消软键盘状态变化监听
    public void removeSoftKeyboardChangedListener(OnSoftKeyboardStateChangedListener listener) {
        if (listener != null) {
            mKeyboardStateListeners.remove(listener);
        }
    }

    private ArrayList<OnSoftKeyboardStateChangedListener> mKeyboardStateListeners;      //软键盘状态监听列表
    private ViewTreeObserver.OnGlobalLayoutListener mLayoutChangeListener;
    private boolean mIsSoftKeyboardShowing;

    @Override
    protected void onResume() {
        super.onResume();
        focusDecorView(true);
    }

    /**
     * 这个方法可以使editText 默认不弹出键盘，不过会使一些空间失去焦点 如 spinner 首次点击无效，可以在没有editText的activity中关闭
     * @param b
     */
    public void focusDecorView(boolean b) {

      ViewGroup v= (ViewGroup) getWindow().getDecorView();
        v.getChildAt(0).setFocusable(b);
        v.getChildAt(0).setFocusableInTouchMode(b);
    }
}
