package com.newmedia.erxeslibrary.configuration;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

import com.newmedia.erxes.basic.type.FieldValueInput;
import com.newmedia.erxeslibrary.DataManager;
import com.newmedia.erxeslibrary.configuration.params.LoginParams;
import com.newmedia.erxeslibrary.configuration.state.State;
import com.newmedia.erxeslibrary.model.Conversation;
import com.newmedia.erxeslibrary.model.ConversationMessage;
import com.newmedia.erxeslibrary.model.FormConnect;
import com.newmedia.erxeslibrary.model.Geo;
import com.newmedia.erxeslibrary.model.KnowledgeBaseTopic;
import com.newmedia.erxeslibrary.model.User;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class Config {

    //static variables
    static private Config config;
    private LoginParams loginParams;
    private State state;
    private String conversationId = null;
//    public boolean isMessengerOnline = false, notifyCustomer;
    private DataManager dataManager;
    private WeakReference<Context> context;
    private FormConnect formConnect;
    private List<FieldValueInput> fieldValueInputs = new ArrayList<>();
    private Geo geo;
    private String geoResponse;

    private KnowledgeBaseTopic knowledgeBaseTopic = new KnowledgeBaseTopic();
    private List<User> supporters = new ArrayList<>();
    private List<Conversation> conversations = new ArrayList<>();
    private List<ConversationMessage> conversationMessages = new ArrayList<>();
    private boolean isFirstStart = false;
    public LoginParams getLoginParams() {
        return loginParams;
    }

    static public Config getInstance(Context context) {
        if (config == null)
            config = new Config(context);
        return config;
    }

    private Config(Context context) {
        dataManager = DataManager.getInstance(context);
        this.context = new WeakReference<>(context);
        LoadDefaultValues();
    }

    private void Init(String brandcode, String WIDGET_API, String API, String WIDGET_API_UPLOAD) {
        this.loginParams = new LoginParams(brandcode,WIDGET_API,API,WIDGET_API_UPLOAD);
        this.loginParams.save(dataManager);
        LoadDefaultValues();
    }



    public void LoadDefaultValues() {
        dataManager = DataManager.getInstance(context.get());
        this.loginParams = new LoginParams();
        this.loginParams.load(dataManager);
        this.state = new State();
        this.state.load(dataManager);
//        changeLanguage(language);
    }

    public boolean isLoggedIn() {
        return dataManager.getDataS(DataManager.customerId) != null;
    }

    public boolean Logout() {
//        messengerParams.logOut(dataManager);
        return true;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.get().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public boolean messenger_status_check() {
        return isNetworkConnected(); //&& isMessengerOnline;
    }

    public State getState() {
        return state;
    }

    public static class Builder {
        private String brand;
        private String WIDGET_API;
        private String API;
        private String uploadHost;
        public Builder(@NonNull String brand) {
            this.brand = brand;
        }
        public Builder setApiHost(String WIDGET_API) {
            this.WIDGET_API = WIDGET_API;
            return this;
        }
        public Builder setSubscriptionHost(String API) {
            this.API = API;
            return this;
        }
        public Builder setUploadHost(String uploadHost) {
            this.uploadHost = uploadHost;
            return this;
        }
        public Config build(Activity context1) {
            Config t = Config.getInstance(context1);
            t.Init(this.brand, this.WIDGET_API, this.API, this.uploadHost);
            return t;
        }
    }
}
