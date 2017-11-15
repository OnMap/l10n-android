
package onmap.co.il.remote.localization.models;

import java.io.Serializable;
import java.util.List;

public class LocalizationModel implements Serializable {

    private List<Language> languages = null;

    public static LocalizationModel fromJson(String json) {
        return new LocalizationModelParser().parse(json);
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }
}
