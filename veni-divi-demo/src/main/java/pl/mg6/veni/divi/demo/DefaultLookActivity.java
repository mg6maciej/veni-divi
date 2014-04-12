package pl.mg6.veni.divi.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.util.Random;

import veni.divi.VeniDivi;

public class DefaultLookActivity extends Activity {

    private VeniDivi veniDivi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.default_look_activity);
        veniDivi = VeniDivi.create(this);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        veniDivi.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        veniDivi.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (!veniDivi.goBack()) {
            super.onBackPressed();
        }
    }

    public void onClick(View view) {
        veniDivi.addElement("[" + new Random().nextInt(10000) + "]");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onHomeClick();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onHomeClick() {
        if (!veniDivi.reset()) {
            finish();
        }
    }
}
