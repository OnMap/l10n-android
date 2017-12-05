package onmap.co.il.remote.localization.storage;

import android.content.Context;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import onmap.co.il.remote.localization.models.LocalizationModel;
import onmap.co.il.remote.localization.models.LocalizedValue;

/**
 * Created by dev on 10.11.17.
 */

public interface LocaleStorage {

    void update(LocalizationModel localizationModel);

    void init(Context context);

    Observable<Map<String, List<LocalizedValue>>> get();

    Observable<Map<String, List<LocalizedValue>>> onLocalizationChanged();
}
