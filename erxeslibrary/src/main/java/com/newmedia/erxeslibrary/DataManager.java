package com.newmedia.erxeslibrary;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.newmedia.erxeslibrary.configuration.state.Messengerdata;
import com.newmedia.erxeslibrary.helper.Json;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by lol on 3/23/16.
 */
public class DataManager {
    private SharedPreferences pref;
    static final private int PRIVATE_MODE = 0;
    // Editor reference for Shared preferences
    private SharedPreferences.Editor editor;
    private static final String PREFER_NAME = "ERXES_LIB";
    public static final String BRANDCODE = "brandcode";

    public static final String WIDGET_API = "widget_api";
    public static final String API = "api";
    public static final String WIDGET_API_UPLOAD = "widget_api_upload";


    public static final String email = "email";
    public static final String phone = "phone";
    public static final String integrationId = "integrationId";
    public static final String customerId = "customerId";
    public static final String color = "color";
    public static final String language = "language";
    public static final String isUser = "isUser";
    public static final String customData = "customData";
    public static final String wallpaper = "wallpaper";

    private static final String messengerData ="messengerData";


    static private DataManager dataManager;
    private WeakReference<Context> _context;

    static public DataManager getInstance(Context context){
        if(dataManager == null)
            dataManager = new DataManager(context);

        return dataManager;
    }
    // Context
    private DataManager(Context context) {
        this._context = new WeakReference<>(context);
        pref = _context.get().getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void setData(String key, String data) {
        editor.putString(key, data);
        editor.commit();
    }
    public String getDataS(String key) {
        return pref.getString(key, null);
    }
    public String getDataS(String key,String default_) {
        return pref.getString(key, default_);
    }
    public void setData(String key, int data) {
        editor.putInt(key, data);
        editor.commit();
    }
    public int getDataI(String key) {
        return pref.getInt(key, 0);
    }
    public void setData(String key, boolean data) {
        editor.putBoolean(key, data);
        editor.commit();
    }
    public boolean getDataB(String key) {
        return pref.getBoolean(key, true);
    }
    public void setMessengerData(String data){
        editor.putString(DataManager.messengerData, data);
        editor.commit();
    }
    public Messengerdata getMessenger(){
        if (pref.getString(DataManager.messengerData, null) != null) {
            try {
                return Messengerdata.convert(new Json(new JSONObject(pref.getString(DataManager.messengerData, null))), pref.getString(language, null));
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else return null;
    }
}
