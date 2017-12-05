package onmap.co.il.remote.localization.models;

import java.util.List;

/**
 * Created by dev on 10.11.17.
 */

public class Language {
    private String locale;
    private List<Pair> data;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public List<Pair> getData() {
        return data;
    }

    public void setData(List<Pair> data) {
        this.data = data;
    }
}
