package onmap.co.il.remote.localization.storage;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import onmap.co.il.remote.localization.models.Language;
import onmap.co.il.remote.localization.models.LocalizationModel;
import onmap.co.il.remote.localization.models.LocalizedValue;
import onmap.co.il.remote.localization.models.Pair;


public class RealmLocaleStorage implements LocaleStorage {

    private static RealmLocaleStorage realmLocaleStorage;


    private final PublishSubject<Map<String, List<LocalizedValue>>> onLocalizationChanged = PublishSubject.create();

    private RealmLocaleStorage() {
        super();
    }

    public static RealmLocaleStorage getInstance() {
        if (realmLocaleStorage == null) {
            realmLocaleStorage = new RealmLocaleStorage();
        }
        return realmLocaleStorage;
    }

    @Override
    public void update(LocalizationModel localizationModel) {

        Realm realm = Realm.getDefaultInstance();
        List<Localization> localizations = mapLocalizations(localizationModel);
        Map<String, List<LocalizedValue>> convert = convert(localizations);
        realm.executeTransaction(realmDb -> realmDb.insertOrUpdate(localizations));
        realm.close();
        onLocalizationChanged.onNext(convert);
    }

    @NonNull
    private List<Localization> mapLocalizations(LocalizationModel localizationModel) {
        List<Language> languages = localizationModel.getLanguages();
        List<Localization> localizations = new ArrayList<>();
        for (int i = 0; i < languages.size(); i++) {
            Language language = languages.get(i);
            String locale = language.getLocale();
            List<Pair> data = language.getData();
            for (int j = 0; j < data.size(); j++) {
                Pair pair = data.get(j);
                Localization localization = new Localization();
                localization.setLanguage(locale);
                String key = pair.getKey();
                localization.setKey(key);
                localization.setValue(pair.getValue());
                localization.setPrimaryKey(key + "_" + locale);
                localizations.add(localization);
            }

        }
        return localizations;
    }

    @Override
    public void init(Context context) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("localization.realm")
                .modules(new LibraryModule())
                .build();
        Realm.deleteRealm(config);
        Realm.setDefaultConfiguration(config);
    }

    @Override
    public Observable<Map<String, List<LocalizedValue>>> get() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Localization.class)
                .findAll()
                .asFlowable()
                .toObservable()
                .map(this::convert);


    }

    @NonNull
    private Map<String, List<LocalizedValue>> convert(List<Localization> localizations) {
        Map<String, List<LocalizedValue>> map = new HashMap<>();
        for (int i = 0; i < localizations.size(); i++) {
            Localization localization = localizations.get(i);
            if (localization != null) {
                String key = localization.getKey();
                List<LocalizedValue> localizedValueList = map.get(key);
                if (localizedValueList == null) {
                    localizedValueList = new ArrayList<>();
                }
                LocalizedValue localizedValue = new LocalizedValue();
                localizedValue.setLanguage(localization.getLanguage());
                localizedValue.setValue(localization.getValue());
                localizedValueList.add(localizedValue);
                map.put(key, localizedValueList);
            }
        }
        return map;
    }

    @Override
    public Observable<Map<String, List<LocalizedValue>>> onLocalizationChanged() {
        return onLocalizationChanged;
    }
}
