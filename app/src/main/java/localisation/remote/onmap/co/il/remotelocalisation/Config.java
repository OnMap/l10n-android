package localisation.remote.onmap.co.il.remotelocalisation;

import android.util.SparseArray;

import onmap.co.il.remote.localization.ConfigMap;


public class Config implements ConfigMap {
    @Override
    public SparseArray<String> getMap() {
        SparseArray<String> map = new SparseArray<>();
        map.append(R.id.text_view, "Test.Key.First");
        return map;
    }
}
