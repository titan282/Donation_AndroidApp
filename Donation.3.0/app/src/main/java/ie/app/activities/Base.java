package ie.app.activities;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ie.app.models.Donation;

public class Base extends AppCompatActivity {
    public final int target = 10000;
    public int totalDonated = 0;
    public static List<Donation> donations = new ArrayList<Donation>();

    public boolean newDonation(Donation donation){
        boolean targetArchived = totalDonated > target;
        if(targetArchived){
            Toast.makeText(this, "Target Exceeded", Toast.LENGTH_SHORT).show();
        }
        else {
            donations.add(donation);
            totalDonated += donation.amount;
        }
        return targetArchived;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem donate = menu.findItem(R.id.menuDonate);
        MenuItem report = menu.findItem(R.id.menuReport);


        if(donations.isEmpty()){
            report.setEnabled(false);
        }
        else{
            report.setEnabled(true);
        }

        if(this instanceof Donate){
            donate.setVisible(false);
            if(!donations.isEmpty()){
                report.setVisible(true);
            }
            else{
                report.setVisible(false);
                donate.setVisible(true);
            }
        }
        if(this instanceof Report){
            report.setVisible(false);
        }
        return true;
    }

    public void report(MenuItem item){
        startActivity(new Intent(this, Report.class));
    }
    public void donate(MenuItem item){
        startActivity(new Intent(this, Donate.class));
    }
    public void settings(MenuItem item){
        Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show();
    }
}
