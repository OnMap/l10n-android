package onmap.co.il.remote.localization.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dev on 10.11.17.
 */

public class LocalizationModelParser {

    public LocalizationModel parse(String body) {
        Type mapType = new TypeToken<Map<String, Map<String, String>>>() {
        }.getType();
        return parse(body, mapType);
    }

    private LocalizationModel parse(String body, Type mapType) {
        LocalizationModel localizationModel = new LocalizationModel();
        List<Language> languages = new ArrayList<>();
        Map<String, Map<String, String>> map = new Gson().fromJson(body, mapType);
        for (Map.Entry<String, Map<String, String>> entry : map.entrySet()) {
            String locale = entry.getKey();
            Map<String, String> dataMap = entry.getValue();
            Language language = new Language();
            language.setLocale(locale);
            List<Pair> data = new ArrayList<>();
            for (Map.Entry<String, String> pair : dataMap.entrySet()) {
                data.add(new Pair(pair.getKey(), pair.getValue()));
            }

            language.setData(data);
            languages.add(language);

        }

        localizationModel.setLanguages(languages);
        return localizationModel;
    }
}
