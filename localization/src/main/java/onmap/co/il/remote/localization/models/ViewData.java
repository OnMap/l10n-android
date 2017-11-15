package onmap.co.il.remote.localization.models;

import java.util.List;

/**
 * Created by dev on 14.11.17.
 */

public class ViewData {
    private String key;
    private List<LocalizedValue> values;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<LocalizedValue> getValues() {
        return values;
    }

    public void setValues(List<LocalizedValue> values) {
        this.values = values;
    }
}
