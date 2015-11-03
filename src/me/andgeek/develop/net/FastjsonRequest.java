package me.andgeek.develop.net;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import me.andgeek.develop.util.LogUtils;

import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

/**
 * Json请求基类 提交表单参数
 * 
 * @author wanglin
 * @param <T>
 */
public class FastjsonRequest<T> extends JsonRequest<T> {
    // GZip类型支持
    private static final String ENCODING_GZIP = "gzip";
    
    private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    
    private static final String COOKIE_KEY = "Cookie";
    
    private boolean mGzipEnabled = false;
    
    private final Class<T> mCls;
    
    private Map<String, String> headers;
    
    private Map<String, String> mMapParams;
    
    // 是否使用缓存，默认false;
    private boolean isUseCache = false;
    
    /**
     * 创建一个新的实例 FastjsonRequest. 默认GET请求
     * 
     * @param url
     *            访问路径
     * @param clazz
     *            返回的实体类型
     * @param jsonRequest
     *            请求的json格式参数
     * @param listener
     *            成功回调
     * @param errorListener
     *            错误回调
     */
    public FastjsonRequest(final String url,
                           final Class<T> clazz,
                           final JSONObject jsonRequest,
                           final Listener<T> listener,
                           final ErrorListener errorListener) {
        this(jsonRequest == null ? Method.GET : Method.POST, url, clazz, jsonRequest, listener, errorListener);
    }
    
    /**
     * 创建一个新的实例 FastjsonRequest.
     * 
     * @param method
     *            请求方式{@link Method}
     * @param url
     *            访问路径
     * @param clazz
     *            返回的实体类型
     * @param jsonRequest
     *            请求的json格式参数
     * @param listener
     *            成功回调
     * @param errorListener
     *            错误回调
     */
    public FastjsonRequest(final int method,
                           final String url,
                           final Class<T> clazz,
                           final JSONObject jsonRequest,
                           final Listener<T> listener,
                           final ErrorListener errorListener) {
        
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener, errorListener);
        this.mCls = clazz;
    }
    
    /**
     * 创建一个新的实例 FastjsonRequest. 默认GET请求
     * 
     * @param url
     *            访问路径
     * @param clazz
     *            返回的实体类型
     * @param mapRequest
     *            请求的Map格式参数
     * @param listener
     *            成功回调
     * @param errorListener
     *            错误回调
     */
    public FastjsonRequest(final String url,
                           final Class<T> clazz,
                           final Map<String, String> mapRequest,
                           final Listener<T> listener,
                           final ErrorListener errorListener) {
        super(Method.GET, url, null, listener, errorListener);
        this.mCls = clazz;
        mMapParams = mapRequest;
    }
    
    /**
     * 创建一个新的实例 FastjsonRequest.
     * 
     * @param method
     *            请求方式{@link Method}
     * @param url
     *            访问路径
     * @param clazz
     *            返回的实体类型
     * @param mapRequest
     *            请求的Map格式参数
     * @param listener
     *            成功回调
     * @param errorListener
     *            错误回调
     */
    public FastjsonRequest(final int method,
                           final String url,
                           final Class<T> clazz,
                           final Map<String, String> mapRequest,
                           final Listener<T> listener,
                           final ErrorListener errorListener) {
        super(method, url, null, listener, errorListener);
        this.mCls = clazz;
        mMapParams = mapRequest;
    }
    
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }
        
        // add zip header
        if (mGzipEnabled) {
            headers.put(HEADER_ACCEPT_ENCODING, ENCODING_GZIP);
        }
        
        // 设置cookie到Http请求头里面去
        setCookie(headers);
        
        return headers;
        
    }
    
    @Override
    protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
        return mMapParams;
    };
    
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = "";
            
            String contentType = response.headers.get(HEADER_ACCEPT_ENCODING);
            
            // ContentType为gzip的话，做zip流处理
            if (ENCODING_GZIP.equalsIgnoreCase(contentType)) {
                GZIPInputStream zis = new GZIPInputStream(new BufferedInputStream(new ByteArrayInputStream(response.data)));
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int count;
                    while ((count = zis.read(buffer)) != -1) {
                        baos.write(buffer, 0, count);
                    }
                    byte[] bytes = baos.toByteArray();
                    jsonString = new String(bytes);
                    baos.close();
                }
                finally {
                    zis.close();
                }
            }
            else {
                jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                
            }
            
            LogUtils.d("response:" + jsonString);
            LogUtils.d("contentType:" + contentType);
            
            // 保持Cookie到本地 ：
            saveSessionCookie(response.headers);
            if (isUseCache) {
                return Response.success(JSON.parseObject(jsonString, mCls),
                                        HttpHeaderParser.parseCacheHeaders(response));
            }
            else {
                return Response.success(JSON.parseObject(jsonString, mCls),
                                        HttpHeaderParser.parseCacheHeaders(response));
            }
            
        }
        catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
        catch (IOException e) {
            return Response.error(new ParseError(e));
        }
    }
    
    /**
     * 禁用Gzip，默认为false
     */
    public void disableGzip() {
        mGzipEnabled = false;
    }
    
    /**
     * 禁用Cache，默认false
     */
    public void disableCache() {
        isUseCache = false;
    }
    
    public boolean isUseCache() {
        return isUseCache;
    }
    
    public void setUseCache(boolean isUseCache) {
        this.isUseCache = isUseCache;
    }
    
    /**
     * 保存服务端cookie到sharedprefrences
     * 
     * @param headers
     */
    public final void saveSessionCookie(Map<String, String> headers) {
        if (headers.containsKey(COOKIE_KEY)) {
            String cookie = headers.get(COOKIE_KEY);
            // 保存cookie
        }
    }
    
    /**
     * 获取本地存储的cookie值
     * 
     * @param headers
     */
    public final void setCookie(Map<String, String> headers) {
        // String sessionId = mCurrentConfig.getString(COOKIE_KEY, "");
        // if (!StringUtils.isEmpty(sessionId)) {
        // headers.put(COOKIE_KEY, sessionId);
        // }
    }
    
}
