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
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Donate 1.0
        donateButton = (Button) findViewById(R.id.donateButton);
        paymentMethod = (RadioGroup) findViewById(R.id.paymentMethod);
        amountPicker = (NumberPicker) findViewById(R.id.amountPicker);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        amountText = (EditText) findViewById(R.id.paymentAmount);
        amountTotal = (TextView) findViewById(R.id.totalSoFar);
        progressBar.setMax(10000);
        amountPicker.setMinValue(0);
        amountPicker.setMaxValue(1000);
        amountTotal.setText("$"+totalDonated);
        Log.d("Tan", ""+totalDonated);
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
            totalDonated += amount;
            progressBar.setProgress(totalDonated);
            amountTotal.setText("$" + totalDonated);
            newDonation(new Donation(amount, method));
        }
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        //Donation2.0
//        switch (id){
//            case R.id.menuReport:
//                startActivity(new Intent(this,Report.class));
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}