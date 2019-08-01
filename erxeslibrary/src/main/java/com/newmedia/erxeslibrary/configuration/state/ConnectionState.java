package com.newmedia.erxeslibrary.configuration.state;

import android.graphics.Color;

import com.newmedia.erxeslibrary.DataManager;

public class ConnectionState {
    public String customerId;
    public String integrationId;
    public String language;
    public int colorCode;

    private String color;
    public void load(DataManager dataManager) {
        this.customerId = dataManager.getDataS(DataManager.customerId);
        this.integrationId = dataManager.getDataS(DataManager.integrationId);
        this.color = dataManager.getDataS(DataManager.color);
        this.language = dataManager.getDataS(DataManager.language);
        this.wallpaper = dataManager.getDataS(DataManager.wallpaper);

        if (this.color != null)
            this.colorCode = Color.parseColor(this.color);
        else
            this.colorCode = Color.parseColor("#5629B6");
    }

    public void save(DataManager dataManager) {

    }
    public void logOut(DataManager dataManager){
        customerId = null;
        dataManager.setData(DataManager.customerId, null);
        dataManager.setData(DataManager.integrationId, null);
    }
    public void changeLanguage(String lang,DataManager dataManager) {
        if (lang == null || lang.equalsIgnoreCase(""))
            return;

        this.language = lang;
        dataManager.setData(DataManager.language, this.language);

//        Locale myLocale;
//        myLocale = new Locale(lang);
//
//        Locale.setDefault(myLocale);
//        android.content.res.Configuration config = new android.content.res.Configuration();
//        config.locale = myLocale;
//        activity.getResources().updateConfiguration(config, activity.getResources().getDisplayMetrics());

    }

}
