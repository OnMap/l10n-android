package localisation.remote.onmap.co.il.remotelocalisation;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity {

    public static final String APP_ID = "2abd2626-9462-46ed-9909-df70befadb73";
    public static final String IOS_APP_ID = "f635ba15-0adf-42bc-b13e-88499a433f37";
    public static final String HOST = "https://parseltongue.onmap.co.il/v1/";
    private BaseApplication baseApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        baseApplication = ((BaseApplication) getApplication());
        Button viewById = findViewById(R.id.Activity_Main_Button);
        viewById
                .setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SecondActivity.class)));

    }

    @Override
    protected void onResume() {
        super.onResume();
        baseApplication.bindRemoteLocalization(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        baseApplication.unbindRemoteLocalization(this);
    }

}
