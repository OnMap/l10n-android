package localisation.remote.onmap.co.il.remotelocalisation;

import android.app.Activity;
import android.app.Application;

import onmap.co.il.remote.localization.RemoteLocalization;

public class BaseApplication extends Application {

    private RemoteLocalization remoteLocalization;

    @Override
    public void onCreate() {
        super.onCreate();
        remoteLocalization = new RemoteLocalization.Builder()
                .setAppId(MainActivity.APP_ID)
                .setHost(MainActivity.HOST)
                .setSocketUri("https://parseltongue.onmap.co.il/")
                .setConfigMap(new Config())//custom mapping
                .build(this, R.id.class);
    }

    public void bindRemoteLocalization(Activity activity) {
        remoteLocalization.bind(activity);
    }

    public void unbindRemoteLocalization(Activity activity) {
        remoteLocalization.unbind(activity);
    }
}
