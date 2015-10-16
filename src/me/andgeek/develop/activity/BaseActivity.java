package me.andgeek.develop.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import me.andgeek.develop.R;
import me.andgeek.develop.net.RequestManager;
import me.andgeek.develop.util.ToastUtils;
import me.andgeek.develop.view.TitleBar;
import me.andgeek.develop.view.TitleBar.OnTitleBarClickListener;
import com.lidroid.xutils.view.annotation.ViewInject;

public class BaseActivity extends FragmentActivity implements OnTitleBarClickListener {
    
    protected Activity mActivity = this;
    
    @ViewInject(R.id.tv_left)
    private TextView mTextViewLeft;
    
    @ViewInject(R.id.tv_right)
    private TextView mTextViewRight;
    
    private TitleBar mTitleBar;
    
    private boolean mTitleBarVisible = true;
    
    @Override
    protected void onCreate(Bundle bundle) {
        // TODO Auto-generated method stub
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestManager.getRequestQueue().cancelAll(mActivity);
    }
    
    @Override
    public void setContentView(int layoutResID) {
        if (mTitleBarVisible) {
            initTitleBar();
            ViewGroup root = genRootView();
            View paramView = getLayoutInflater().inflate(layoutResID, null);
            root.addView(mTitleBar, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            root.addView(paramView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            super.setContentView(root);
        }
        else {
            super.setContentView(layoutResID);
        }
        
    }
    
    /** 设置TopBar */
    public void initTitleBar() {
        mTitleBar = new TitleBar(this);
        mTitleBar.setVisibility(View.VISIBLE);
        mTitleBar.setOnTitleBarClickListener(this);
    }
    
    /**
     * @description
     * @date 2015-1-27
     * @param
     * @return ViewGroup
     * @Exception
     */
    private ViewGroup genRootView() {
        LinearLayout localLinearLayout = new LinearLayout(this);
        localLinearLayout.setOrientation(LinearLayout.VERTICAL);
        return localLinearLayout;
    }
    
    /**
     * @description 是否显示标题栏，true显示，false不显示，默认true.
     *              在onCreate之后setContentView之前调用
     * @date 2015年8月27日
     * @param visible
     */
    protected void setTitleBarVisible(boolean visible) {
        mTitleBarVisible = visible;
    }
    
    @Override
    public void onLeftClick() {
        ToastUtils.showMessage("onLeftClick");
    }
    
    @Override
    public void onMiddleClick() {
        ToastUtils.showMessage("onMiddleClick");
    }
    
    @Override
    public void onRightClick() {
        ToastUtils.showMessage("onRightClick");
    }
    
    /**
     * 设置栏标题
     */
    @Override
    public void onTitle(String title) {
        ToastUtils.showMessage("onTitle");
    }
}
