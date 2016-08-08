package com.android.ebeijia.androidlibrary.customView;



import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.android.ebeijia.androidlibrary.R;


public class CustomProgressDialog extends ProgressDialog {
	private TextView tv_text;
    private String text;
    public CustomProgressDialog(Context context) {
        super(context);  
    }  
      
    public CustomProgressDialog(Context context, int theme) {
    	
        super(context, theme);  
    }  
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressdialog);
        tv_text=(TextView) this.findViewById(R.id.mytext);
        tv_text.setText(this.text);
    }  
    public void setText(String text){
        this.text=text;
    	if(tv_text!=null){
    		tv_text.setText(this.text);
    	}
    }
}  
