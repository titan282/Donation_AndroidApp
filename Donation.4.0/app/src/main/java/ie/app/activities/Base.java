package ie.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ie.app.main.DonationApp;
import ie.app.models.Donation;

public class Base extends AppCompatActivity {
    public DonationApp app;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (DonationApp) getApplication();
        app.dbManager.open();
        app.dbManager.setTotalDonated(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.dbManager.close();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem donate = menu.findItem(R.id.menuDonate);
        MenuItem report = menu.findItem(R.id.menuReport);
        MenuItem reset = menu.findItem(R.id.menuReset);

        if(app.dbManager.getAll().isEmpty()){
            report.setEnabled(false);
            reset.setEnabled(false);
        }
        else{
            report.setEnabled(true);
            reset.setEnabled(true);
        }

        if(this instanceof Donate){
            donate.setVisible(false);
            if(!app.dbManager.getAll().isEmpty()){
                report.setVisible(true);
                reset.setVisible(true);
            }
            else{
                report.setEnabled(false);
                reset.setEnabled(false);
            }
        }
        if(this instanceof Report){
            report.setVisible(false);
            reset.setVisible(false);
        }
        return true;
    }

    public void report(MenuItem item){
        startActivity(new Intent(this, Report.class));
    }
    public void donate(MenuItem item){
        startActivity(new Intent(this, Donate.class));
    }
    public void reset(MenuItem item){    }
}
