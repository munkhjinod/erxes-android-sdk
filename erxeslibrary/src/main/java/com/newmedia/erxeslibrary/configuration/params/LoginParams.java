package com.newmedia.erxeslibrary.configuration.params;

import com.newmedia.erxeslibrary.DataManager;

public class LoginParams {
    //erxes-widgets-api
    public String WIDGET_API = "";
    //erxes-api
    public String API = "";
    //erxes-widgets-api + upload_url
    public String WIDGET_API_UPLOAD = "";

    public String BRANDCODE;
    public void load(DataManager dataManager) {
        WIDGET_API = dataManager.getDataS(DataManager.WIDGET_API);
        API = dataManager.getDataS(DataManager.API);
        WIDGET_API_UPLOAD = dataManager.getDataS(DataManager.WIDGET_API_UPLOAD);
        BRANDCODE = dataManager.getDataS(DataManager.BRANDCODE);
    }

    public void save(DataManager dataManager) {
        dataManager.setData(DataManager.WIDGET_API, WIDGET_API);
        dataManager.setData(DataManager.API, API);
        dataManager.setData(DataManager.WIDGET_API_UPLOAD, WIDGET_API_UPLOAD);
        dataManager.setData(DataManager.BRANDCODE, BRANDCODE);

    }

    public LoginParams(String brandcode,String WIDGET_API, String API, String WIDGET_API_UPLOAD) {
        this.WIDGET_API = WIDGET_API;
        this.API = API;
        this.WIDGET_API_UPLOAD = WIDGET_API_UPLOAD;
        this.BRANDCODE = brandcode;
    }

    public LoginParams() {
    }
}
