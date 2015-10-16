package me.andgeek.develop.net;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.FileEntity;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import me.andgeek.develop.util.LogUtils;

public class MultipartRequest<T> extends Request<T> {
    
    private final Response.Listener<T> mListener;
    
    private final File mFile;
    
    protected Map<String, String> headers;
    
    private String CONTENT_TYPE = "binary/octet-stream";
    
    private Class<T> mCls;
    
    public MultipartRequest(String url, Class<T> cls, File file, Listener<T> listener, ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        mCls = cls;
        mListener = listener;
        mFile = file;
    }
    
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();
        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }
        headers.put("Accept", "application/json");
        return headers;
    }
    
    @Override
    public String getBodyContentType() {
        return CONTENT_TYPE;
    }
    
    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            FileEntity fileEntity = new FileEntity(mFile, CONTENT_TYPE);
            fileEntity.writeTo(bos);
        }
        catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream bos, building the multipart request.");
        }
        return bos.toByteArray();
    }
    
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            LogUtils.d("response: " + jsonString);
            return Response.success(JSON.parseObject(jsonString, mCls), HttpHeaderParser.parseCacheHeaders(response));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }
    
    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
