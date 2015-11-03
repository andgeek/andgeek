package me.andgeek.develop.activity;

import java.util.Map;

import me.andgeek.develop.net.OnLoadFinishListener;
import me.andgeek.develop.net.RequestClient;
import me.andgeek.develop.net.RequestManager;
import me.andgeek.develop.util.ToastUtils;
import me.andgeek.develop.view.TitleBar;
import me.andgeek.develop.view.TitleBar.OnTitleBarClickListener;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.android.volley.VolleyError;

public class BaseActivity<T> extends FragmentActivity implements OnTitleBarClickListener, OnLoadFinishListener<T> {
    
    /**
     * 当前上下文
     */
    protected Activity mActivity = this;
    
    /**
     * 顶部标题栏
     */
    private TitleBar mTitleBar;
    
    /**
     * 标题栏是否可见，true可见，false不可见，默认可见
     */
    private boolean mTitleBarVisible = true;
    
    /**
     * 每次网络请求的唯一标识
     */
    private String mTag;
    
    @Override
    protected void onCreate(Bundle bundle) {
        // TODO Auto-generated method stub
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestManager.getRequestQueue().cancelAll(mTag);
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
    
    /** 初始化标题栏TopBar */
    public void initTitleBar() {
        mTitleBar = new TitleBar(this);
        mTitleBar.setVisibility(View.VISIBLE);
        mTitleBar.setOnTitleBarClickListener(this);
    }
    
    /**
     * @description 获取根视图
     * @date 2015年11月3日
     * @return
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
    
    /**
     * 左侧按钮点击事件，默认finish当前界面
     */
    @Override
    public void onLeftClick() {
        ToastUtils.showMessage("onLeftClick");
    }
    
    /**
     * 中间标题点击事件，默认空实现
     */
    @Override
    public void onMiddleClick() {
        ToastUtils.showMessage("onMiddleClick");
    }
    
    /**
     * 右侧按钮点击事件，默认空实现
     */
    @Override
    public void onRightClick() {
        ToastUtils.showMessage("onRightClick");
    }
    
    /**
     * 设置顶部栏标题
     */
    @Override
    public void onTitle(String title) {
        ToastUtils.showMessage("onTitle");
        mTitleBar.getMiddleButton().setText(title);
    }
    
    public void execute(String tag, String loadingMsg, String url, Class<T> responceClass, Map<String, String> params) {
        mTag = tag;
        RequestClient<T> client = new RequestClient<T>(mActivity);
        client.setOnLoadCompleteListener(this);
        client.executeWithTags(tag, loadingMsg, url, responceClass, params);
    }
    
    @Override
    public void onSuccess(T response) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void onError(VolleyError error) {
        // TODO Auto-generated method stub
        
    }
}
