package com.newmedia.erxeslibrary.configuration.state;

import android.graphics.Color;

import com.newmedia.erxeslibrary.DataManager;
import com.newmedia.erxeslibrary.helper.Json;

public class State {
    private String customerId;
    private String integrationId;
    private String language;

    private Messengerdata messengerdata;
    private UiOptions uiOptions;

    public void load(DataManager dataManager) {
        this.customerId = dataManager.getDataS(DataManager.customerId);
        this.integrationId = dataManager.getDataS(DataManager.integrationId);
        this.language = dataManager.getDataS(DataManager.language);
        this.messengerdata = Messengerdata.getMessengerData(dataManager);
        this.uiOptions = UiOptions.getUiOptions(dataManager);
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
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getIntegrationId() {
        return integrationId;
    }

    public String getLanguage() {
        if(language == null)
            return "en";
        return language;
    }

    public Messengerdata getMessengerdata() {
        return messengerdata;
    }

    public UiOptions getUiOptions() {
        return uiOptions;
    }

    public void setMessengerdata(Json json, DataManager dataManager) {
        if(json == null)
            return;
        dataManager.setMessengerData(json.toString());

        this.messengerdata = Messengerdata.convert(json,this.language);
    }
}
