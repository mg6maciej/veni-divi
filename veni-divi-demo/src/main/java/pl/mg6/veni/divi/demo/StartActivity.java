package pl.mg6.veni.divi.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
    }

    public void onDefaultLookClick(View view) {
        startActivity(new Intent(this, DefaultLookActivity.class));
    }

    public void onFragmentsInBackStackClick(View view) {
        startActivity(new Intent(this, FragmentsInBackStackActivity.class));
    }

    public void onConfigurationOptionsClick(View view) {
        startActivity(new Intent(this, ConfigurationOptionsActivity.class));
    }
}
