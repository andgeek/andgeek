package me.andgeek.develop.net;

import com.android.volley.VolleyError;

public interface OnLoadFinishListener<Response> {
    /**
     * 数据加载成功
     * 
     * @param response
     *            the response
     */
    void onSuccess(Response response);
    
    /**
     * 数据加载失败
     * 
     * @param error
     *            the error
     */
    void onError(VolleyError error);
}
