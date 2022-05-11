package ie.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;

import ie.app.api.ApiService;
import ie.app.main.DonationApp;
import ie.app.models.Donation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Base extends AppCompatActivity {
    public DonationApp app;
    public boolean isCallApiDone=false;
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
        callApi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        app.totalDonated = sumDonate(app.donations);
        Toast.makeText(Base.this,""+app.donations.isEmpty(),Toast.LENGTH_SHORT).show();

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
    public void reset(MenuItem item){    }
    private void callApiSync() throws IOException {
        List<Donation> donations= ApiService.apiService.getAnswers().execute().body();
    }
    private void callApi() {

        ApiService.apiService.getAnswers().enqueue(
                new Callback<List<Donation>>() {

                    @Override
                    public void onResponse(Call<List<Donation>> call, Response<List<Donation>> response) {
                        Toast.makeText(Base.this,"Call thanh cong",Toast.LENGTH_SHORT).show();
                        Toast.makeText(Base.this,response.body().toString(),Toast.LENGTH_SHORT).show();
                        app.donations.addAll(response.body());
                        isCallApiDone=true;
                    }

                    @Override
                    public void onFailure(Call<List<Donation>> call, Throwable t) {
                        Toast.makeText(Base.this,"Call that bai",Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
    private int sumDonate(List<Donation> donations){
        int sum =0;
        for(int i=0;i<donations.size();i++){
            sum+=donations.get(i).amount;
        }
        return sum;
    }
}
