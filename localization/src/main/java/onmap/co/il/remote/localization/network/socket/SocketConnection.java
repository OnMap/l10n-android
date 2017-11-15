package onmap.co.il.remote.localization.network.socket;

import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import onmap.co.il.remote.localization.models.LocalizationModel;
import onmap.co.il.remote.localization.storage.LocaleStorage;
import onmap.co.il.remote.localization.storage.RealmLocaleStorage;

import static onmap.co.il.remote.localization.Logger.log;

/**
 * Created by dev on 10.11.17.
 */

public class SocketConnection {

    private Socket socket;

    private SocketConnection(String uri, String appId) throws URISyntaxException {
        socket = IO.socket(uri);
        connectionState();
        LocaleStorage storage = RealmLocaleStorage.getInstance();
        socket.on(appId, args -> {
            for (Object object : args) {
                log(object.toString());
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

    private void connectionState() {
        socket
                .on(Socket.EVENT_CONNECT, args -> log("connect"))
                .on(Socket.EVENT_CONNECT_ERROR, args -> log("connect error"))
                .on(Socket.EVENT_ERROR, args -> log("event error"))
                .on(Socket.EVENT_DISCONNECT, args -> log("event disconnect"));
    }


    public void connect() {
        socket = socket.connect();
    }
}
