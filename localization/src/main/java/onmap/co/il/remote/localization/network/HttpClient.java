package onmap.co.il.remote.localization.network;

import android.support.annotation.NonNull;

import java.io.IOException;

import io.reactivex.Observable;
import onmap.co.il.remote.localization.Logger;
import onmap.co.il.remote.localization.Result;
import onmap.co.il.remote.localization.models.LocalizationModel;


/**
 * Created by dev on 09.11.17.
 */

public class HttpClient {
    private String host;

    public HttpClient(String host) {

        this.host = host;
    }

    public Observable<LocalizationModel> getTranslations(String appId) {
        return Observable.create(emitter -> HttpClient.this.getTranslations(appId, new Result<LocalizationModel>() {
            @Override
            public void onSuccess(LocalizationModel result) {
                emitter.onNext(result);
                emitter.onComplete();
            }

            @Override
            public void onError(Throwable throwable) {
                emitter.onError(throwable);
            }
        }));
    }

    public void getTranslations(String appId, Result<LocalizationModel> result) {
        ApiConnection apiConnection = getApiConnection();
        try {
            LocalizationModel model = getResponse(appId, apiConnection);
            result.onSuccess(model);
        } catch (IOException e) {
            e.printStackTrace();
            result.onError(e);
        }
    }

    public LocalizationModel getTranslationsSync(String appId) {
        ApiConnection apiConnection = getApiConnection();
        try {
            return getResponse(appId, apiConnection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private LocalizationModel getResponse(String appId, ApiConnection apiConnection) throws IOException {
        String body = apiConnection.get(host + "translations?app_id=" + appId);
        Logger.log(body);
        return LocalizationModel.fromJson(body);
    }


    @NonNull
    private ApiConnection getApiConnection() {
        return new ApiConnection(new ApiConnection.Timeout(60 * 1000, 60 * 1000));
    }


}
