package com.android.ebeijia.androidlibrary.customView;

import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.android.ebeijia.androidlibrary.utils.Log;

/**
 * Created by wuqinghai on 16/3/28.
 */
public class ShowAndHideCheckBox extends CheckBox {
    private EditText editText;
    public ShowAndHideCheckBox(Context context) {
       this(context, null);
    }

    public ShowAndHideCheckBox(Context context, AttributeSet attrs) {
       this(context, attrs, 0);
    }

    public ShowAndHideCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setClickable(true);
        setChecked(true);
        //setFocusable(true);
        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    //如果选中，显示密码
                    if(editText!=null) {
                        editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    Log.i("显示密码","显示密码");
                }else{
                    //否则隐藏密码
                    if(editText!=null) {
                        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                    Log.i("隐藏密码","隐藏密码");
                }

            }
        });

    }
    public void setTarget(EditText editText){
        this.editText=editText;
    }


}
