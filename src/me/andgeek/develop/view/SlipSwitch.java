package me.andgeek.develop.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * 滑动开关自定义视图 使用说明： 1、首先初始化图片setImageResource 2、设置监听器可选
 * 
 * @author summer
 */
public class SlipSwitch extends View implements OnTouchListener {
    
    private Bitmap switch_on_Bkg, switch_off_Bkg, slip_Btn; // 开的背景，关的背景，滑块
    
    private Rect on_Rect, off_Rect; // 开关矩形
            
    private boolean isSlipping = false;
    
    private boolean isSwitchOn = false; // 默认选项项
    
    private float previousX, currentX;
    
    private OnSwitchListener onSwitchListener;
    
    public SlipSwitch(Context context) {
        super(context);
        init();
    }
    
    public SlipSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    private void init() {
        setOnTouchListener(this);
    }
    
    /**
     * 设置开关图片
     * 
     * @param switchOnBkg
     *            开的背景图片
     * @param switchOffBkg
     *            关的背景图片
     * @param slipBtn
     *            滑块图片
     */
    public void setImageResource(int switchOnBkg, int switchOffBkg, int slipBtn) {
        switch_on_Bkg = BitmapFactory.decodeResource(getResources(), switchOnBkg);
        switch_off_Bkg = BitmapFactory.decodeResource(getResources(), switchOffBkg);
        slip_Btn = BitmapFactory.decodeResource(getResources(), slipBtn);
        
        on_Rect = new Rect(switch_off_Bkg.getWidth() - slip_Btn.getWidth(),
                           0,
                           switch_off_Bkg.getWidth(),
                           slip_Btn.getHeight());
        off_Rect = new Rect(0, 0, slip_Btn.getWidth(), slip_Btn.getHeight());
    }
    
    /**
     * 改变开关状态
     * 
     * @param switchState
     */
    public void setSwitchState(boolean switchState) {
        isSwitchOn = switchState;
        if (isSwitchOn) {
            currentX = (switch_on_Bkg.getWidth() / 2) + 1;
        }
        else {
            currentX = (switch_on_Bkg.getWidth() / 2) - 1;
        }
        invalidate();
    }
    
    /**
     * 获取开关状态
     * 
     * @return
     */
    public boolean getSwitchState() {
        return isSwitchOn;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        
        Matrix matrix = new Matrix();
        Paint paint = new Paint();
        float left_SlipBtn;
        
        if (currentX < (switch_on_Bkg.getWidth() / 2)) {
            canvas.drawBitmap(switch_off_Bkg, matrix, paint);
        }
        else {
            canvas.drawBitmap(switch_on_Bkg, matrix, paint);
        }
        
        if (isSlipping) {
            if (currentX > switch_on_Bkg.getWidth()) {
                left_SlipBtn = switch_on_Bkg.getWidth() - slip_Btn.getWidth();
            }
            else {
                left_SlipBtn = currentX - slip_Btn.getWidth() / 2;
            }
        }
        else {
            if (isSwitchOn) {
                left_SlipBtn = on_Rect.left;
            }
            else {
                left_SlipBtn = off_Rect.left;
            }
        }
        
        if (left_SlipBtn < 0) {
            left_SlipBtn = 0;
        }
        else if (left_SlipBtn > switch_on_Bkg.getWidth() - slip_Btn.getWidth()) {
            left_SlipBtn = switch_on_Bkg.getWidth() - slip_Btn.getWidth();
        }
        int topY = (switch_off_Bkg.getHeight() - slip_Btn.getHeight()) / 2;// 居中
        canvas.drawBitmap(slip_Btn, left_SlipBtn, topY, paint);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        setMeasuredDimension(switch_on_Bkg.getWidth(), switch_on_Bkg.getHeight());
    }
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                break;
            
            case MotionEvent.ACTION_DOWN:
                if (event.getX() > switch_on_Bkg.getWidth() || event.getY() > switch_on_Bkg.getHeight()) {
                    return false;
                }
                
                isSlipping = true;
                previousX = event.getX();
                currentX = previousX;
                break;
            
            case MotionEvent.ACTION_UP:
                isSlipping = false;
                boolean previousSwitchState = isSwitchOn;
                
                if (event.getX() >= (switch_on_Bkg.getWidth() / 2)) {
                    isSwitchOn = true;
                }
                else {
                    isSwitchOn = false;
                }
                
                if (onSwitchListener != null && (previousSwitchState != isSwitchOn)) {
                    onSwitchListener.onSwitched(isSwitchOn);
                }
                break;
        }
        
        invalidate();
        return true;
    }
    
    /**
     * 设置选择监听器
     * 
     * @param listener
     */
    public void setOnSwitchListener(OnSwitchListener listener) {
        onSwitchListener = listener;
    }
    
    public interface OnSwitchListener {
        abstract void onSwitched(boolean isSwitchOn);
    }
    
}
