package me.andgeek.develop.activity;

import java.util.HashMap;

import me.andgeek.develop.R;
import me.andgeek.develop.model.BaseModel;
import android.os.Bundle;

public class MainActivity extends BaseActivity<BaseModel> {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitleBarVisible(false);
        super.onCreate(savedInstanceState);
        setTitleBarVisible(false);
        setContentView(R.layout.activity_main);
        
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("client_type", "sale");
        param.put("name", "ttest");
        param.put("pwd", "111111");
        param.put("nowver", "20151022");
        param.put("task", "s_user_login");
        param.put("serverver", "v2");
        param.put("sign", "eb4e5aeca5f2ee16577a6d5951b25648");
        execute("dfdf", "正在加载", "http://192.168.0.230?", BaseModel.class, param);
    }
    
    @Override
    public void onSuccess(BaseModel response) {
        super.onSuccess(response);
    }
}
