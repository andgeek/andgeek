package me.andgeek.develop.activity;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

import com.android.volley.VolleyError;
import me.andgeek.develop.R;
import me.andgeek.develop.model.BaseModel;
import me.andgeek.develop.net.OnLoadFinishListener;
import me.andgeek.develop.net.RequestClient;
import me.andgeek.develop.util.ToastUtils;

public class MainActivity extends BaseActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitleBarVisible(false);
        super.onCreate(savedInstanceState);
        setTitleBarVisible(false);
        setContentView(R.layout.activity_main);
        
        RequestClient<BaseModel> client = new RequestClient<BaseModel>(mActivity);
        client.setOnLoadCompleteListener(new OnLoadFinishListener<BaseModel>() {
            
            @Override
            public void onSuccess(BaseModel response) {
                ToastUtils.showMessage(response.msg);
            }
            
            @Override
            public void onError(VolleyError error) {
                ToastUtils.showMessage("登录成功");
                
            }
        });
        JSONObject param = new JSONObject();
        try {
            param.put("Phone", "18180641988");
            param.put("Password", "965EB72C92A549DD");
        }
        catch (JSONException e1) {
            e1.printStackTrace();
        }
        //client.execute("正在加载", "http://211.149.216.40:7777/api/Login", BaseModel.class, param);
        
        
        RequestClient<BaseModel> client1 = new RequestClient<BaseModel>(mActivity);
        client1.setOnLoadCompleteListener(new OnLoadFinishListener<BaseModel>() {
            
            @Override
            public void onSuccess(BaseModel response) {
                ToastUtils.showMessage(response.msg);
            }
            
            @Override
            public void onError(VolleyError error) {
                ToastUtils.showMessage("登录成功");
                
            }
        });
        File file = new File(getExternalCacheDir(),"aaa");
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        client1.executeFileUpload("正在加载", "http://211.149.216.40:7777/api/UpFile", BaseModel.class, file);
    }
}
