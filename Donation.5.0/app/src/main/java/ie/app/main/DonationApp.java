package ie.app.main;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import ie.app.activities.Donate;
import ie.app.api.ApiService;
import ie.app.models.Donation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonationApp extends Application {

    public final int target = 10000;
    public int totalDonated =0;
    public List <Donation> donations = new ArrayList<Donation>();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Application", "Hello from Donation App");
    }

    public boolean newDonation(Donation donation, Context context){
        boolean targetArchived = totalDonated > target;
        if(targetArchived){
            Toast.makeText(this, "Target Exceeded", Toast.LENGTH_SHORT).show();
        }
        else {
            donations.add(donation);
            totalDonated += donation.amount;
            ApiService.apiService.addDonation(donation).enqueue(new Callback<Donation>() {
                @Override
                public void onResponse(Call<Donation> call, Response<Donation> response) {
                    Toast.makeText(context,"Add Donation Successfully",Toast.LENGTH_SHORT);
                }

                @Override
                public void onFailure(Call<Donation> call, Throwable t) {

                }
            });
        }
        return targetArchived;
    }
}
