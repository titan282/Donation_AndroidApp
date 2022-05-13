package ie.app.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import ie.app.activities.databinding.ActivityMainBinding;
import ie.app.api.ApiService;
import ie.app.main.DonationApp;
import ie.app.models.Donation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Donate extends Base {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private Button donateButton;
    private RadioGroup paymentMethod;
    private NumberPicker amountPicker;
    private ProgressBar progressBar;
    private EditText amountText;
    private TextView amountTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        amountTotal.setText("$" + app.totalDonated);
        progressBar.setProgress(app.totalDonated);
        callApi();
    }

    public void donateButtonPressed(View view) {
        int amount = amountPicker.getValue();
        int radioId = paymentMethod.getCheckedRadioButtonId();
        String method = radioId == R.id.Paypal ? "Paypal" : "Direct";
        if (amount == 0) {
            String text = amountText.getText().toString();
            if (!text.equals("")) {
                amount = Integer.parseInt(text);
            }
        }
        if (amount > 0) {
            app.newDonation(new Donation(amount, method, 1), this);
            Log.v("Donation", "" + amount + " " + app.totalDonated);
            progressBar.setProgress(app.totalDonated);
            amountTotal.setText("$" + app.totalDonated);
        }
    }


    @Override
    public void reset(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All Donation?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setMessage("Are you sure you want to Delete all Donations?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                app.totalDonated = 0;
                progressBar.setProgress(app.totalDonated);
                amountTotal.setText("$" + app.totalDonated);
                resetDialog = new ProgressDialog(Donate.this, 1);
                resetDialog.setMessage("Deleting Donations....");
                resetDialog.show();
                deleteAll();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();





    }

    private void deleteAll() {
        List<Donation> temp = new ArrayList<Donation>();
        temp.addAll(app.donations);
        for (int i = 0; i <= temp.size() - 1; i++) {
            ApiService.apiService.deleteItem(temp.get(i).id).enqueue(new Callback<Donation>() {
                @Override
                public void onResponse(Call<Donation> call, Response<Donation> response) {
                    Donation donation = response.body();
                    Log.d("donationList", app.donations.size() + "");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (app.donations.isEmpty()) {
                                if (resetDialog.isShowing())
                                    resetDialog.dismiss();
                            }
                        }
                    });
                    app.donations.remove(donation);
                    callApi();
                }

                @Override
                public void onFailure(Call<Donation> call, Throwable t) {
                    Log.d("donationList", "Loi");
                }
            });
        }
    }

    public void init() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        donateButton = (Button) findViewById(R.id.donateButton);
        paymentMethod = (RadioGroup) findViewById(R.id.paymentMethod);
        amountPicker = (NumberPicker) findViewById(R.id.amountPicker);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        amountText = (EditText) findViewById(R.id.paymentAmount);
        amountTotal = (TextView) findViewById(R.id.totalSoFar);
        progressBar.setMax(10000);
        amountPicker.setMinValue(0);
        amountPicker.setMaxValue(1000);
    }

    private void callApi() {

        ApiService.apiService.getAnswers().enqueue(
                new Callback<List<Donation>>() {

                    @Override
                    public void onResponse(Call<List<Donation>> call, Response<List<Donation>> response) {
                        Toast.makeText(Donate.this, "Call Succesfully", Toast.LENGTH_SHORT).show();
                        app.donations = response.body();
                        if (app.donations == null) {
                            app.donations = new ArrayList<>();
                        }
                        app.totalDonated = sumDonate(app.donations);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                amountTotal.setText("" + app.totalDonated);
                                progressBar.setProgress(app.totalDonated);
                            }
                        });
                        app.totalDonated = sumDonate(app.donations);
                        if (loadingDialog.isShowing()) {
                            loadingDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Donation>> call, Throwable t) {
                        Toast.makeText(Donate.this, "Call that bai", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    private int sumDonate(List<Donation> donations) {
        int sum = 0;
        for (int i = 0; i < donations.size(); i++) {
            sum += donations.get(i).amount;
        }
        return sum;
    }




}