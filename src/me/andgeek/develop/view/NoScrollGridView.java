package me.andgeek.develop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 不滚动的GridView视图
 * @description
 * @author summer
 * @date 2014-10-12 下午10:43:06
 */
public class NoScrollGridView extends GridView {

	public NoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public NoScrollGridView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public NoScrollGridView(Context context) {
		this(context, null);
	}
	
	@Override     
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	   
	   int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
	     MeasureSpec.AT_MOST);            
	   super.onMeasure(widthMeasureSpec, expandSpec);
	   
	}
}
