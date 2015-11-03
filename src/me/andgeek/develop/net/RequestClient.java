package me.andgeek.develop.net;

import java.io.File;
import java.util.Map;

import me.andgeek.develop.BaseApplication;
import me.andgeek.develop.util.StringUtils;
import me.andgeek.develop.util.ToastUtils;
import me.andgeek.develop.view.LoadingDialog;

import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Build;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.VolleyError;

/**
 * 请求处理监听
 */
public class RequestClient<T> {
    
    /** 加载完成监听器. */
    private OnLoadFinishListener<T> mOnLoadCompleteListener;
    
    /** 数据请求加载进度 */
    private LoadingDialog mLoadingDialog;
    
    private final String DEFAULT_LOAD_MSG = "正在努力加载...";
    
    /** 是否使用缓存 */
    private boolean isUseCache = false;
    
    /** 是否使用进度条， 默认使用 */
    private boolean isUseProgress = true;
    
    private Context mContext;
    
    /**
     * 无网络链接
     */
    public final int NET_UNAVAILABLE = 0;
    
    /**
     * 2g/3g网络
     */
    public final int NET_MOBILE = 1;
    
    /**
     * wifi
     */
    public final int NET_WIFI = 2;
    
    private final String NETWORK_MSG_ERROR = "网络不可用，请检查你的网络连接";
    
    public RequestClient(Context context) {
        this.mContext = context;
    }
    
    public boolean isUseCache() {
        return isUseCache;
    }
    
    public void setUseCache(boolean isUseCache) {
        this.isUseCache = isUseCache;
    }
    
    public void setUseProgress(boolean isUseProgress) {
        this.isUseProgress = isUseProgress;
    }
    
    public Context getContext() {
        return mContext;
    }
    
    public void setContext(Context context) {
        this.mContext = context;
    }
    
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean isActivityAlive() {
        if (mContext == null) {
            return false;
        }
        Activity activity = (Activity) mContext;
        if (Build.VERSION.SDK_INT > 16) {
            if (activity.isDestroyed() || activity.isFinishing()) {
                return false;
            }
        }
        else {
            if (activity.isFinishing()) {
                return false;
            }
        }
        
        return true;
    }
    
    private int detectNetwork() {
        Context ctx = BaseApplication.getInstance();
        ConnectivityManager manager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        State mobile = null;
        if (networkInfo != null) {
            mobile = networkInfo.getState();
        }
        networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        State wifi = null;
        if (networkInfo != null) {
            wifi = networkInfo.getState();
        }
        
        if (wifi == State.CONNECTED || wifi == State.CONNECTING)
            return NET_WIFI;
        if (mobile == State.CONNECTED || mobile == State.CONNECTING)
            return NET_MOBILE;
        
        return NET_UNAVAILABLE;
    }
    
    private void dismissDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing() && isActivityAlive()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }
    
    /**
     * 显示进度条
     * 
     * @param mContext
     * @param loadingMsg
     */
    private void showDialog(String loadingMsg) {
        if (null == mLoadingDialog && isActivityAlive()) {
            if (StringUtils.isNullOrEmpty(loadingMsg)) {
                loadingMsg = DEFAULT_LOAD_MSG;
            }
            mLoadingDialog = new LoadingDialog(mContext);
            mLoadingDialog.show(loadingMsg);
        }
    }
    
    // //////////////////////////////////////////////////////Json参数-开始//////////////////////////////////////////////////////////////////////////////
    /**
     * @description 请求服务器数据
     * @date 2015年9月7日
     * @param loadingMsg
     *            对话框提示文字
     * @param url
     *            请求地址
     * @param responceClass
     *            返回实体类型
     * @param params
     *            参数Json形式
     */
    public void execute(String loadingMsg, String url, Class<T> responceClass, JSONObject params) {
        
        if (detectNetwork() == NET_UNAVAILABLE) {
            ToastUtils.showMessage(NETWORK_MSG_ERROR);
            return;
        }
        
        if (StringUtils.isNullOrEmpty(url)) {
            return;
        }
        
        if (mOnLoadCompleteListener == null) {
            throw new IllegalArgumentException("OnLoadCompleteListener is null. You must call setOnLoadCompleteListener before.");
        }
        
        if (isUseProgress) {
            showDialog(loadingMsg);
        }
        
        // 执行Http请求
        FastjsonRequest<T> request = new FastjsonRequest<T>(Method.POST,
                                                            url,
                                                            responceClass,
                                                            params,
                                                            successListener,
                                                            errorListener);
        // 设置缓存
        request.setUseCache(isUseCache);
        // 设置连接超时时间
        setRetryPolicy(request);
        request.setTag(mContext);
        RequestManager.getRequestQueue().add(request);
    }
    
    /**
     * 发送服务端数据请求,有Dialog模式
     * 
     * @param mContext
     * @param tag
     * @param url
     * @param loadingMsg
     * @param responceClass
     * @param params
     */
    public void executeWithTags(Object tag, String loadingMsg, String url, Class<T> responceClass, JSONObject params) {
        if (detectNetwork() == NET_UNAVAILABLE) {
            ToastUtils.showMessage(NETWORK_MSG_ERROR);
            return;
        }
        
        if (StringUtils.isNullOrEmpty(url))
            return;
        
        if (mOnLoadCompleteListener == null)
            throw new IllegalArgumentException("OnLoadCompleteListener is null. You must call setOnLoadCompleteListener before.");
        
        if (isUseProgress)
            showDialog(loadingMsg);
        
        // 执行Http请求
        FastjsonRequest<T> request = new FastjsonRequest<T>(Method.POST,
                                                            url,
                                                            responceClass,
                                                            params,
                                                            successListener,
                                                            errorListener);
        
        // 设置缓存
        request.setUseCache(isUseCache);
        // 设置连接超时时间
        setRetryPolicy(request);
        // 设置连接超时时间
        request.setTag(tag);
        RequestManager.getRequestQueue().add(request);
    }
    
    /**
     * @description
     * @date 2015年10月13日
     * @param url
     * @param msg
     * @param responceClass
     * @param file
     */
    public void executeFileUpload(String loadingMsg, String url, Class<T> responceClass, File file) {
        if (detectNetwork() == NET_UNAVAILABLE) {
            ToastUtils.showMessage(NETWORK_MSG_ERROR);
            return;
        }
        
        if (StringUtils.isNullOrEmpty(url))
            return;
        
        if (mOnLoadCompleteListener == null) {
            throw new IllegalArgumentException("OnLoadCompleteListener is null. You must call setOnLoadCompleteListener before.");
        }
        
        if (isUseProgress) {
            showDialog(loadingMsg);
        }
        
        MultipartRequest<T> request = new MultipartRequest<T>(url, responceClass, file, successListener, errorListener);
        // 设置缓存
        // 设置连接超时时间
        setRetryPolicy(request);
        // 设置连接超时时间
        request.setTag(mContext);
        RequestManager.getRequestQueue().add(request);
    }
    
    // //////////////////////////////////////////////////////Map参数-开始//////////////////////////////////////////////////////////////////////////////
    /**
     * @description 请求服务器数据
     * @date 2015年9月7日
     * @param loadingMsg
     *            对话框提示文字
     * @param url
     *            请求地址
     * @param responceClass
     *            返回实体类型
     * @param params
     *            参数Map形式
     */
    public void execute(String loadingMsg, String url, Class<T> responceClass, Map<String, String> params) {
        
        if (detectNetwork() == NET_UNAVAILABLE) {
            ToastUtils.showMessage(NETWORK_MSG_ERROR);
            return;
        }
        
        if (StringUtils.isNullOrEmpty(url)) {
            return;
        }
        
        if (mOnLoadCompleteListener == null) {
            throw new IllegalArgumentException("OnLoadCompleteListener is null. You must call setOnLoadCompleteListener before.");
        }
        
        if (isUseProgress) {
            showDialog(loadingMsg);
        }
        
        // 执行Http请求
        FastjsonRequest<T> request = new FastjsonRequest<T>(Method.POST,
                                                            url,
                                                            responceClass,
                                                            params,
                                                            successListener,
                                                            errorListener);
        // 设置缓存
        request.setUseCache(isUseCache);
        // 设置连接超时时间
        setRetryPolicy(request);
        request.setTag(mContext);
        RequestManager.getRequestQueue().add(request);
    }
    
    /**
     * 发送服务端数据请求,有Dialog模式
     * 
     * @param tag
     * @param url
     * @param loadingMsg
     * @param responceClass
     * @param params
     */
    public void executeWithTags(Object tag,
                                String loadingMsg,
                                String url,
                                Class<T> responceClass,
                                Map<String, String> params) {
        if (detectNetwork() == NET_UNAVAILABLE) {
            ToastUtils.showMessage(NETWORK_MSG_ERROR);
            return;
        }
        
        if (StringUtils.isNullOrEmpty(url))
            return;
        
        if (mOnLoadCompleteListener == null)
            throw new IllegalArgumentException("OnLoadCompleteListener is null. You must call setOnLoadCompleteListener before.");
        
        if (isUseProgress)
            showDialog(loadingMsg);
        
        // 执行Http请求
        FastjsonRequest<T> request = new FastjsonRequest<T>(Method.POST,
                                                            url,
                                                            responceClass,
                                                            params,
                                                            successListener,
                                                            errorListener);
        
        // 设置缓存
        request.setUseCache(isUseCache);
        // 设置连接超时时间
        setRetryPolicy(request);
        // 设置连接超时时间
        request.setTag(tag);
        RequestManager.getRequestQueue().add(request);
    }
    
    /**
     * @description
     * @date 2015年10月13日
     * @param url
     * @param msg
     * @param responceClass
     * @param file
     */
    public void executeFileUpload(String loadingMsg, String url, Class<T> responceClass, Map<String, String> params) {
        if (detectNetwork() == NET_UNAVAILABLE) {
            ToastUtils.showMessage(NETWORK_MSG_ERROR);
            return;
        }
        
        if (StringUtils.isNullOrEmpty(url))
            return;
        
        if (mOnLoadCompleteListener == null) {
            throw new IllegalArgumentException("OnLoadCompleteListener is null. You must call setOnLoadCompleteListener before.");
        }
        
        if (isUseProgress) {
            showDialog(loadingMsg);
        }
        // TODO 需要修改
        MultipartRequest<T> request = new MultipartRequest<T>(url, responceClass, null, successListener, errorListener);
        // 设置缓存
        // 设置连接超时时间
        setRetryPolicy(request);
        // 设置连接超时时间
        request.setTag(mContext);
        RequestManager.getRequestQueue().add(request);
    }
    
    // //////////////////////////////////////////////////////Map参数-结束//////////////////////////////////////////////////////////////////////////////
    
    /**
     * 设置连接重试和timeout
     * 
     * @param <T>
     * @param request
     */
    private void setRetryPolicy(Request<T> request) {
        request.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
    
    /**
     * 接收服务端响应成功
     */
    private com.android.volley.Response.Listener<T> successListener = new com.android.volley.Response.Listener<T>() {
        
        @Override
        public void onResponse(T response) {
            dismissDialog();
            
            if (response == null) {
                ToastUtils.showMessage("网络访问异常，请稍后重试");
                return;
            }
            
            if (mOnLoadCompleteListener != null) {
                mOnLoadCompleteListener.onSuccess(response);
            }
        }
    };
    
    /**
     * 服务端响应 失败 或者 请求连接失败
     */
    private com.android.volley.Response.ErrorListener errorListener = new com.android.volley.Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            dismissDialog();
            if (mOnLoadCompleteListener != null) {
                mOnLoadCompleteListener.onError(error);
            }
        }
    };
    
    public OnLoadFinishListener<T> getOnLoadCompleteListener() {
        return mOnLoadCompleteListener;
    }
    
    public void setOnLoadCompleteListener(OnLoadFinishListener<T> onLoadCompleteListener) {
        mOnLoadCompleteListener = onLoadCompleteListener;
    }
}
