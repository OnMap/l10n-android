package onmap.co.il.remote.localization.network.socket;

import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import onmap.co.il.remote.localization.models.LocalizationModel;
import onmap.co.il.remote.localization.storage.LocaleStorage;
import onmap.co.il.remote.localization.storage.RealmLocaleStorage;


/**
 * Created by dev on 10.11.17.
 */

public class SocketConnection {

    private Socket socket;

    private SocketConnection(String uri, String appId) throws URISyntaxException {
        socket = IO.socket(uri);
        LocaleStorage storage = RealmLocaleStorage.getInstance();
        socket.on(appId, args -> {
            for (Object object : args) {
                if (object instanceof JSONObject) {
                    JSONObject json = (JSONObject) object;

                    LocalizationModel localizationModel = LocalizationModel.fromJson(json.toString());
                    storage.update(localizationModel);
                }
            }

        });
    }

    public static SocketConnection create(String uri, String appId) throws URISyntaxException {
        return new SocketConnection(uri, appId);
    }


    public void connect() {
        socket = socket.connect();
    }
}
