package com.newmedia.erxeslibrary;

import android.content.Context;
import android.content.Intent;

import com.newmedia.erxeslibrary.configuration.Config;
import com.newmedia.erxeslibrary.configuration.ErxesRequest;
import com.newmedia.erxeslibrary.ui.login.ErxesActivity;

import org.json.JSONObject;

public class ErxesSDK {
    static private DataManager dataManager;
    static void initialize(Config config){
//        config = Config.getInstance(this);
//        erxesRequest = ErxesRequest.getInstance(config);
    }

    static public void start(Context context) {
        DataManager dataManager = DataManager.getInstance(context);
        dataManager.setData(DataManager.isUser, false);
        dataManager.setData(DataManager.email, null);
        dataManager.setData(DataManager.phone, null);
        dataManager.setData(DataManager.customData, null);
        Intent a = new Intent(context, ErxesActivity.class);
        a.putExtra("hasData",false);
        context.startActivity(a);
    }

    static public void start(String email, String phone, JSONObject jsonObject) {
        dataManager.setData(DataManager.isUser, true);
        dataManager.setData(DataManager.email, email);
        dataManager.setData(DataManager.phone, phone);
        dataManager.setData(DataManager.customData, jsonObject.toString());
//        Intent a = new Intent(activity.get(), ErxesActivity.class);
//        a.putExtra("hasData",true);
//        a.putExtra("customData", jsonObject.toString());
//        a.putExtra("mEmail",email);
//        a.putExtra("mPhone",phone);
//        activity.get().startActivity(a);
//        erxesRequest.add(this);
    }
}

