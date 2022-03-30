package ie.app.main;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ie.app.database.DBManager;
import ie.app.models.Donation;

public class DonationApp extends Application {

    public final int target = 10000;
    public int totalDonated =0;
    public DBManager dbManager;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Application", "Hello from Donation App");
        dbManager = new DBManager(this);
    }

    public boolean newDonation(Donation donation){
        boolean targetArchived = totalDonated > target;
        if(targetArchived){
            Toast.makeText(this, "Target Exceeded", Toast.LENGTH_SHORT).show();
        }
        else {
            dbManager.add(donation);
            totalDonated += donation.amount;
        }
        return targetArchived;
    }
}
