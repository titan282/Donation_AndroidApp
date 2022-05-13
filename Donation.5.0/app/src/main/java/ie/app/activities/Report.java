package ie.app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ie.app.adapter.DonationAdapter;
import ie.app.api.ApiService;
import ie.app.models.Donation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Report extends Base {
    ListView listView;
    ImageButton deleteBtn;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog deleteDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callApi();
        setContentView(R.layout.activity_report);
        listView = (ListView) findViewById(R.id.reportList);
        swipeRefreshLayout = findViewById(R.id.report_swipe_refresh_layout);
        DonationAdapter adapter = new DonationAdapter(this, app.donations);
        listView.setAdapter(adapter);
        refresh();
        deleteDialog = new ProgressDialog(Report.this, 1);
        deleteDialog.setMessage("Deleting Donation");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Report.this,"ID: "+app.donations.get(i).id,Toast.LENGTH_SHORT).show();
                view.findViewById(R.id.imgDelete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onDonationDelete(app.donations.get(i));
                    }
                });
            }
        });
    }

    private void refresh() {
        DonationAdapter adapter = new DonationAdapter(this, app.donations);
        listView.setAdapter(adapter);
        listView.invalidate();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callApi();
            }
        });
    }

    private void callApi() {

        ApiService.apiService.getAnswers().enqueue(
                new Callback<List<Donation>>() {

                    @Override
                    public void onResponse(Call<List<Donation>> call, Response<List<Donation>> response) {
                        Toast.makeText(Report.this,"Call Succesfully",Toast.LENGTH_SHORT).show();
                        app.donations=response.body();
                        if(app.donations==null){
                            app.donations=new ArrayList<>();
                        }
                        DonationAdapter adapter = new DonationAdapter(Report.this, app.donations);
                        listView.setAdapter(adapter);
                        swipeRefreshLayout.setRefreshing(false);
                        if (loadingDialog.isShowing()) {
                            loadingDialog.dismiss();
                        }
                        if(deleteDialog.isShowing()){
                            deleteDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Donation>> call, Throwable t) {
                        Toast.makeText(Report.this,"Call that bai",Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
    public void onDonationDelete(final Donation donation) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Donation?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setMessage("Are you sure you want to Delete the \'Donation with ID \' \n [ "
                + donation.id + " ] ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteDonation(donation.id);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void deleteDonation(int id) {
        deleteDialog.show();
        ApiService.apiService.deleteItem(id).enqueue(new Callback<Donation>() {
            @Override
            public void onResponse(Call<Donation> call, Response<Donation> response) {
                Toast.makeText(Report.this,"Delete succesfully",Toast.LENGTH_SHORT).show();
                callApi();
            }

            @Override
            public void onFailure(Call<Donation> call, Throwable t) {

            }
        });
    }
}