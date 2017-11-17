package localisation.remote.onmap.co.il.remotelocalisation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by dev on 15.11.17.
 */

public class SecondActivity extends Activity {

    private BaseApplication baseApplication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        baseApplication = ((BaseApplication) getApplication());
    }

    @Override
    protected void onResume() {
        super.onResume();
        baseApplication.bindRemoteLocalization(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        baseApplication.unbindRemoteLocalization();
    }
}
