package me.andgeek.develop.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * 自定义带图标的TextView视图，可以实现图片文字居中
 * 
 * @description
 * @author summer
 * @date 2014-10-12 下午10:43:06
 */
public class DrawableCenterTextView extends TextView {
    public DrawableCenterTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    public DrawableCenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public DrawableCenterTextView(Context context) {
        super(context);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            Drawable drawableLeft = drawables[0];
            Drawable drawableTop = drawables[1];
            Drawable drawableRight = drawables[2];
            Drawable drawableBottom = drawables[3];
            if (drawableLeft != null) {
                setGravity(Gravity.CENTER_VERTICAL);
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = 0;
                drawableWidth = drawableLeft.getIntrinsicWidth();
                float bodyWidth = textWidth + drawableWidth + drawablePadding;
                canvas.translate((getWidth() - bodyWidth) / 2, 0);
            }
            if (drawableTop != null) {
                setGravity(Gravity.CENTER_HORIZONTAL);
                int drawablePadding = getCompoundDrawablePadding();
                float textHeight = getPaint().getTextSize();
                int drawableHeight = 0;
                drawableHeight = drawableTop.getIntrinsicWidth();
                float bodyHeight = textHeight + drawableHeight + drawablePadding;
                canvas.translate(0, (getHeight() - bodyHeight) / 2);
            }
            if (drawableRight != null) {
                setGravity(Gravity.CENTER_VERTICAL);
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = 0;
                drawableWidth = drawableRight.getIntrinsicWidth();
                float bodyWidth = drawableWidth + textWidth + drawablePadding;
                setPadding(0, 0, (int) (getWidth() - bodyWidth), 0);
                canvas.translate((getWidth() - bodyWidth) / 2, 0);
            }
            if (drawableBottom != null) {
                setGravity(Gravity.CENTER_HORIZONTAL);
                int drawablePadding = getCompoundDrawablePadding();
                float textHeight = getPaint().getTextSize();
                int drawableHeight = 0;
                drawableHeight = drawableBottom.getIntrinsicHeight();
                float bodyHeight = textHeight + drawableHeight + drawablePadding;
                setPadding(0, 0, 0, (int) (getHeight() - bodyHeight));
                canvas.translate(0, (getHeight() - bodyHeight) / 2);
            }
        }
        super.onDraw(canvas);
    }
}
