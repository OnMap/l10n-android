package onmap.co.il.remote.localization;

import android.app.Activity;
import android.content.Context;

import java.net.URISyntaxException;

import io.reactivex.schedulers.Schedulers;
import onmap.co.il.remote.localization.network.HttpClient;
import onmap.co.il.remote.localization.network.socket.SocketConnection;
import onmap.co.il.remote.localization.storage.LocaleStorage;
import onmap.co.il.remote.localization.storage.RealmLocaleStorage;

/**
 * Created by dev on 10.11.17.
 */

public class RemoteLocalization {
    private String appId;
    private String host;
    private String socketUri;
    private ConfigMap config;
    private ViewBinder binder;


    private void setup(Context context, Class aClass) {
        if (appId == null || host == null) {
            throw new IllegalArgumentException("Please set your APP_ID and host url");
        }
        setupStorage(context);
        updateLocalizations(appId, host);
        openRealTimeConnection();
        binder = new ViewBinder(context, aClass, config);
    }


    private void setupStorage(Context context) {
        LocaleStorage storage = RealmLocaleStorage.getInstance();
        storage.init(context);

    }

    private void openRealTimeConnection() {
        try {
            SocketConnection socketConnection = SocketConnection.create(socketUri, appId);
            socketConnection.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void updateLocalizations(String appId, String host) {
        final HttpClient httpClient = new HttpClient(host);
        LocaleStorage storage = RealmLocaleStorage.getInstance();
        httpClient.getTranslations(appId)
                .subscribeOn(Schedulers.io())
                .subscribe(storage::update);
    }

    public void bind(Activity activity) {
        binder.bind(activity);
    }

    public void unbind(Activity activity) {
        binder.unbind(activity);
    }

    public static class Builder {

        private final RemoteLocalization remoteLocalization;

        public Builder() {
            remoteLocalization = new RemoteLocalization();
        }

        public Builder setAppId(String appId) {
            remoteLocalization.appId = appId;
            return this;
        }

        public Builder setHost(String host) {
            remoteLocalization.host = host;
            return this;
        }

        public Builder setSocketUri(String uri) {
            remoteLocalization.socketUri = uri;
            return this;
        }

        public Builder setConfigMap(ConfigMap config) {
            remoteLocalization.config = config;
            return this;
        }

        public RemoteLocalization build(Context context, Class aClass) {
            remoteLocalization.setup(context, aClass);
            return remoteLocalization;
        }
    }
}
