package com.android.ebeijia.androidlibrary.customView;



import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.android.ebeijia.androidlibrary.R;

public class DeleteEditText extends EditText{
	private Context context;

	private Drawable image_normal;
	private Drawable image_press;
	
	
	
	public DeleteEditText(Context context) {
		
		super(context);
		this.context=context;
		init();
		
	}
	




	public DeleteEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		init();
	}
	private void init() {

		image_normal=context.getResources().getDrawable(R.drawable.del_normal);
		image_press=context.getResources().getDrawable(R.drawable.del_release);
		this.setCompoundDrawablesWithIntrinsicBounds(null, null, image_normal, null);

		
		
		
		
		
		
		
		
		
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int x=(int) event.getX();
		int y=(int) event.getY();
		boolean isInnerWidth=(x>getWidth()-getTotalPaddingRight())&&(x<getWidth()-getPaddingRight());
		Rect rect= image_normal.getBounds();
		int distence=(getHeight()-rect.height())/2;
		boolean isInnerHeight=(y>distence&&y<distence+rect.height());
		

		if(event.getAction()==MotionEvent.ACTION_DOWN){
			if(isInnerWidth&&isInnerHeight){
				this.setCompoundDrawablesWithIntrinsicBounds(null, null, image_press, null);

			}

		}
		else if(event.getAction()==MotionEvent.ACTION_UP){



			if(isInnerWidth&&isInnerHeight){
				this.setCompoundDrawablesWithIntrinsicBounds(null, null, image_normal, null);
				setText("");
			}
		}
		else {
			this.setCompoundDrawablesWithIntrinsicBounds(null, null, image_normal, null);
		}


		
		
		
		return super.onTouchEvent(event);
	}
	
	
	
	
	
	
	
	
}
