package me.andgeek.develop.activity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import me.andgeek.develop.R;
import me.andgeek.develop.model.BaseModel;
import me.andgeek.develop.net.OnLoadFinishListener;
import me.andgeek.develop.net.RequestClient;
import me.andgeek.develop.util.ToastUtils;
import android.os.Bundle;

import com.android.volley.VolleyError;

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
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("client_type", "sale");
        param.put("name", "ttest");
        param.put("pwd", "111111");
        param.put("nowver", "20151022");
        param.put("task", "s_user_login");
        param.put("serverver", "v2");
        param.put("sign", "eb4e5aeca5f2ee16577a6d5951b25648");
        client.execute("正在加载", "http://192.168.0.230?", BaseModel.class, param);
        
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
        File file = new File(getExternalCacheDir(), "aaa");
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        // client1.executeFileUpload("正在加载",
        // "http://211.149.216.40:7777/api/UpFile", BaseModel.class, file);
    }
}
