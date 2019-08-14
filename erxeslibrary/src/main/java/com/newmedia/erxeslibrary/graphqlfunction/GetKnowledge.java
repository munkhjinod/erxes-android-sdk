package com.newmedia.erxeslibrary.graphqlfunction;

import android.app.Activity;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloCallback;
import com.apollographql.apollo.ApolloQueryWatcher;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.api.cache.http.HttpCachePolicy;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.rx2.Rx2Apollo;
import com.newmedia.erxes.basic.FaqGetQuery;
import com.newmedia.erxeslibrary.configuration.Config;
import com.newmedia.erxeslibrary.configuration.ErxesRequest;
import com.newmedia.erxeslibrary.configuration.ReturnType;
import com.newmedia.erxeslibrary.model.KnowledgeBaseTopic;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;

public class GetKnowledge {
    final static String TAG = "GetKnowledge";
    private ErxesRequest ER;
    private Config config;
    public interface Callback {
        void onResponse(KnowledgeBaseTopic knowledgeBaseTopic);
    }
    private Callback callback;
    public GetKnowledge(ErxesRequest ER, Activity activity) {
        this.ER = ER;
        config = Config.getInstance(activity);
    }

    public void run(Callback mycallback) {
        this.callback = mycallback;
        String knowledgeId = config.getState().getMessengerdata().getKnowledgeBaseTopicId();

        Rx2Apollo.from(ER.getApolloClient()
                .query(FaqGetQuery.builder().topicId(knowledgeId)
                .build())
                .httpCachePolicy(HttpCachePolicy.CACHE_FIRST)
                .watcher())
//                .timeInterval(TimeUnit.SECONDS).timeInterval()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(request);

    }
    private Observer<Response<FaqGetQuery.Data>> request = new Observer<Response<FaqGetQuery.Data>>() {
        @Override
        public void onSubscribe(Disposable disposable) {

        }

        @Override
        public void onNext(Response<FaqGetQuery.Data> response) {
            if (!response.hasErrors()) {
                callback.onResponse(KnowledgeBaseTopic.convert(response.data()));
                ER.notefyAll(ReturnType.FAQ, null, null);
            } else {
                Log.e(TAG, "errors " + response.errors().toString());
                ER.notefyAll(ReturnType.SERVERERROR, null, response.errors().get(0).message());
            }
        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onComplete() {

        }
    };

}
