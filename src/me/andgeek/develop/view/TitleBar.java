package me.andgeek.develop.view;

import me.andgeek.develop.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 自定义的标题栏
 */
public class TitleBar extends LinearLayout {
    
    private Context mContext;
    
    /**
     * 左侧按钮
     */
    @ViewInject(R.id.tv_left)
    private TextView mTvLeft;
    
    /**
     * 右侧
     */
    @ViewInject(R.id.tv_right)
    private TextView mTvRight;
    
    /**
     * 中间标题
     */
    @ViewInject(R.id.tv_middle)
    private TextView mTvMiddle;
    
    private OnTitleBarClickListener mOnTitleBarClickListener;
    
    /**
     * 创建一个新的实例 TitleBar.
     * 
     * @param context
     */
    public TitleBar(Context context) {
        super(context);
        mContext = context;
        init();
    }
    
    /**
     * 创建一个新的实例 TitleBar.
     * 
     * @param context
     * @param attrs
     */
    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }
    
    protected void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.title_bar, this);
        ViewUtils.inject(view);
    }
    
    public void setOnTitleBarClickListener(OnTitleBarClickListener listener) {
        mOnTitleBarClickListener = listener;
    }
    
    @OnClick({ R.id.tv_left, R.id.tv_right, R.id.tv_middle })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                mOnTitleBarClickListener.onLeftClick();
                break;
            case R.id.tv_right:
                mOnTitleBarClickListener.onRightClick();
                break;
            case R.id.tv_middle:
                mOnTitleBarClickListener.onMiddleClick();
                break;
            
            default:
                break;
        }
    }
    
    /** 按钮点击事件接口 */
    public interface OnTitleBarClickListener {
        /** TitleBar 左侧按钮点击事件 */
        public void onLeftClick();
        
        /** TitleBar 中间按钮点击事件 */
        public void onMiddleClick();
        
        /** TitleBar 右侧按钮点击事件 */
        public void onRightClick();
        
        public void onTitle(String title);
    }
    
    public TextView getLeftButton() {
        return mTvLeft;
    }
    
    public TextView getRightButton() {
        return mTvRight;
    }
    
    public TextView getMiddleButton() {
        return mTvMiddle;
    }
}
