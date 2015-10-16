package me.andgeek.develop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 不滚动的ListView视图
 * @description
 * @author summer
 * @date 2014-10-12 下午10:43:06
 */

public class NoScrollListView extends ListView {
    
    public NoScrollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    public NoScrollListView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }
    
    public NoScrollListView(Context context) {
        this(context, null);
    }
    
    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
    
}
