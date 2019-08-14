package com.newmedia.erxeslibrary.configuration;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.ResponseField;
import com.apollographql.apollo.cache.normalized.CacheKey;
import com.apollographql.apollo.cache.normalized.CacheKeyResolver;
import com.apollographql.apollo.cache.normalized.NormalizedCacheFactory;
import com.apollographql.apollo.cache.normalized.sql.ApolloSqlHelper;
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory;
import com.apollographql.apollo.subscription.WebSocketSubscriptionTransport;

import com.newmedia.erxes.basic.type.AttachmentInput;
import com.newmedia.erxes.basic.type.CustomType;
import com.newmedia.erxeslibrary.configuration.params.LoginParams;
import com.newmedia.erxeslibrary.graphqlfunction.GetGEO;
import com.newmedia.erxeslibrary.graphqlfunction.GetKnowledge;
import com.newmedia.erxeslibrary.ErxesObserver;
import com.newmedia.erxeslibrary.graphqlfunction.GetInteg;
import com.newmedia.erxeslibrary.graphqlfunction.GetLead;
import com.newmedia.erxeslibrary.graphqlfunction.GetSup;
import com.newmedia.erxeslibrary.graphqlfunction.Getconv;
import com.newmedia.erxeslibrary.graphqlfunction.Getmess;
import com.newmedia.erxeslibrary.graphqlfunction.Insertmess;
import com.newmedia.erxeslibrary.graphqlfunction.Insertnewmess;
import com.newmedia.erxeslibrary.graphqlfunction.SendLead;
import com.newmedia.erxeslibrary.graphqlfunction.SetConnect;
import com.newmedia.erxeslibrary.helper.JsonCustomTypeAdapter2;


import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ErxesRequest {
    final private String TAG = ErxesRequest.class.getName();

    private ApolloClient apolloClient;
    private OkHttpClient okHttpClient;
    private WeakReference<Activity> activity;
    private List<ErxesObserver> observers;
    private Config config;

    static private ErxesRequest erxesRequest;

    static public ErxesRequest getInstance(Config config) {
        if (erxesRequest == null)
            erxesRequest = new ErxesRequest(config);
        return erxesRequest;
    }

    private ErxesRequest(Config config) {
        this.config = config;
    }

    public ApolloClient getApolloClient() {
        return apolloClient;
    }

    public void set_client(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        LoginParams loginParams = config.getLoginParams();

        // Create the ApolloSqlHelper. Please note that if null is passed in as the name, you will get an in-memory
        // Sqlite database that will not persist across restarts of the app.
        ApolloSqlHelper apolloSqlHelper = ApolloSqlHelper.create(context, "db_name");

        // Create NormalizedCacheFactory
        NormalizedCacheFactory cacheFactory = new SqlNormalizedCacheFactory(apolloSqlHelper);

        // Create the cache key resolver, this example works well when all types have globally unique ids.
        CacheKeyResolver resolver = new CacheKeyResolver() {
            @NotNull
            @Override
            public CacheKey fromFieldRecordSet(@NotNull ResponseField field, @NotNull Map<String, Object> recordSet) {
                return formatCacheKey((String) recordSet.get("id"));
            }

            @NotNull
            @Override
            public CacheKey fromFieldArguments(@NotNull ResponseField field, @NotNull Operation.Variables variables) {
                return formatCacheKey((String) field.resolveArgument("id", variables));
            }

            private CacheKey formatCacheKey(String id) {
                if (id == null || id.isEmpty()) {
                    return CacheKey.NO_KEY;
                } else {
                    return CacheKey.from(id);
                }
            }
        };

        if (loginParams.WIDGET_API != null) {
            okHttpClient = new OkHttpClient.Builder()
//                    .connectionSpecs(Collections.singletonList(spec))
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .addInterceptor(new AddCookiesInterceptor(this.activity.get()))
                    .addInterceptor(new ReceivedCookiesInterceptor(this.activity.get()))
                    .build();
            apolloClient = ApolloClient.builder()
                    .serverUrl(loginParams.WIDGET_API)
                    .normalizedCache(cacheFactory, resolver)
                    .okHttpClient(okHttpClient)
                    .subscriptionTransportFactory(new WebSocketSubscriptionTransport.Factory(loginParams.API, okHttpClient))
//                    .addCustomTypeAdapter(CustomType.JSON, new JsonCustomTypeAdapter())
                    .addCustomTypeAdapter(CustomType.JSON, new JsonCustomTypeAdapter2())
                    .build();
        }
    }

    public void setConnect(String email, String phone, boolean isUser, String data) {
        if (!isNetworkConnected()) {
            return;
        }
        SetConnect setConnect = new SetConnect(this, activity.get());
        setConnect.run(email, phone, isUser, data);
    }

    public void getGEO() {
        if (!isNetworkConnected()) {
            return;
        }
        GetGEO getGEO = new GetGEO(this, activity.get());
        getGEO.run();
    }

    public void getIntegration() {
        if (!isNetworkConnected()) {
            return;
        }
        GetInteg getIntegration = new GetInteg(this, activity.get());
        getIntegration.run();
    }

    public void InsertMessage(String message, String conversationId, List<AttachmentInput> list) {
        if (!isNetworkConnected()) {
            return;
        }
        Insertmess insertmessage = new Insertmess(this, activity.get());
        insertmessage.run(message, conversationId, list);
    }

    public void InsertNewMessage(final String message, List<AttachmentInput> list) {
        if (!isNetworkConnected()) {
            return;
        }

        Insertnewmess insertnewmessage = new Insertnewmess(this, activity.get());
        insertnewmessage.run(message, list);
    }

    public void getConversations() {
        if (!isNetworkConnected()) {
            return;
        }
        Getconv getconversation = new Getconv(this, activity.get());
        getconversation.run();


    }

    public void getMessages(String conversationid) {
        if (!isNetworkConnected()) {
            return;
        }
        Getmess getmess = new Getmess(this, activity.get());
        getmess.run(conversationid);

    }

    public void getSupporters() {
        if (!isNetworkConnected()) {
            return;
        }
        GetSup getSup = new GetSup(this, activity.get());
        getSup.run();
    }

    public void getFAQ(GetKnowledge.Callback callback) {
        if (!isNetworkConnected()) {
            return;
        }
        GetKnowledge getSup = new GetKnowledge(this, activity.get());
        getSup.run(callback);
    }

    public void getLead() {
        if (!isNetworkConnected()) {
            return;
        }
        GetLead getLead = new GetLead(this, activity.get());
        getLead.run();
    }

    public void sendLead() {
        if (!isNetworkConnected()) {
            return;
        }
        SendLead sendLead = new SendLead(this, activity.get());
        sendLead.run();
    }

    public void add(ErxesObserver e) {
        if (observers == null)
            observers = new ArrayList<>();
        observers.clear();
        observers.add(e);
    }

    public boolean isNetworkConnected() {
        if (activity != null && activity.get() != null) {
            ConnectivityManager cm = (ConnectivityManager) activity.get().getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo() != null;
        }
        return false;
    }

    public void remove(ErxesObserver e) {
        if (observers == null)
            observers = new ArrayList<>();
        observers.clear();
    }

    public void notefyAll(int returnType, String conversationId, String message) {
        if (observers == null) return;
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).notify(returnType, conversationId, message);
        }
    }
}
