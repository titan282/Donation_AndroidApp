package ie.app.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

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

import org.w3c.dom.Text;

import ie.app.activities.databinding.ActivityMainBinding;
import ie.app.main.DonationApp;
import ie.app.models.Donation;

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
        amountTotal.setText("$"+app.totalDonated);
        progressBar.setProgress(app.totalDonated);
    }

    public void donateButtonPressed(View view){
        int amount = amountPicker.getValue();
        int radioId = paymentMethod.getCheckedRadioButtonId();
        String method = radioId == R.id.Paypal? "Paypal" : "Direct";
        if(amount == 0){
            String text = amountText.getText().toString();
            if(!text.equals("")){
                amount = Integer.parseInt(text);
            }
        }
        if(amount > 0){
            app.newDonation(new Donation(amount, method));
            Log.v("Donation", ""+amount+ " "+ app.totalDonated);
            progressBar.setProgress(app.totalDonated);
            amountTotal.setText("$" + app.totalDonated);
        }
    }

    @Override
    public void reset(MenuItem item) {
       app.dbManager.reset();
       app.totalDonated=0;
       progressBar.setProgress(app.totalDonated);
       amountTotal.setText("$"+app.totalDonated);
    }

}