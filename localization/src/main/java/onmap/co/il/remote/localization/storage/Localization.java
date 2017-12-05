package onmap.co.il.remote.localization.storage;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dev on 10.11.17.
 */

public class Localization extends RealmObject {

    @PrimaryKey
    private String primaryKey;
    private String key;
    private String value;
    private String language;

    public String getKey() {
        return key;
    }


    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }
}
