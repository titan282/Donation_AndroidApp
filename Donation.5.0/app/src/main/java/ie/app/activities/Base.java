package ie.app.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ie.app.api.ApiService;
import ie.app.main.DonationApp;
import ie.app.models.Donation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Base extends AppCompatActivity {
    public DonationApp app;
    protected ProgressDialog loadingDialog;
    protected ProgressDialog resetDialog;
    int i=0;
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
        loading();
    }

    private void loading() {
        loadingDialog = new ProgressDialog(Base.this, 1);
        loadingDialog.setMessage("Retrieving Donations List");
        loadingDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem donate = menu.findItem(R.id.menuDonate);
        MenuItem report = menu.findItem(R.id.menuReport);
        MenuItem reset = menu.findItem(R.id.menuReset);

//        if(app.dbManager.getAll().isEmpty()){
//            report.setEnabled(false);
//            reset.setEnabled(false);
//        }
//        else{
//            report.setEnabled(true);
//            reset.setEnabled(true);
//        }
//
//        if(this instanceof Donate){
//            donate.setVisible(false);
//            if(!app.dbManager.getAll().isEmpty()){
//                report.setVisible(true);
//                reset.setVisible(true);
//            }
//            else{
//                report.setEnabled(false);
//                reset.setEnabled(false);
//            }
//        }
//        if(this instanceof Report){
//            report.setVisible(false);
//            reset.setVisible(false);
//        }
        return true;
    }

    public void report(MenuItem item){
        startActivity(new Intent(this, Report.class));
    }
    public void donate(MenuItem item){
        startActivity(new Intent(this, Donate.class));
    }
    public void reset(MenuItem item){
        resetDialog = new ProgressDialog(Base.this, 1);
        resetDialog.setMessage("Deleting Donations....");
        resetDialog.show();

        for(i=0;i<=app.donations.size()-1;i++){
            ApiService.apiService.deleteItem(app.donations.get(i).id).enqueue(new Callback<Donation>() {
                @Override
                public void onResponse(Call<Donation> call, Response<Donation> response) {
                        if(resetDialog.isShowing())
                        resetDialog.dismiss();
                }

                @Override
                public void onFailure(Call<Donation> call, Throwable t) {

                }
            });
        }
    }
}
