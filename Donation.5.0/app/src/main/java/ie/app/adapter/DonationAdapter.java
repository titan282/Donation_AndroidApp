package ie.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ie.app.activities.R;
import ie.app.models.Donation;

public class DonationAdapter extends ArrayAdapter<Donation> {
    private Context context;
    public List<Donation> donations;
    public DonationAdapter(Context context, List<Donation> donations) {
        super(context, R.layout.row_donate, donations);
        this.context = context;
        this.donations = donations;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_donate, parent, false);
        Donation donation = donations.get(position);
        TextView amountView = (TextView) view.findViewById(R.id.row_amount);
        TextView methodView = (TextView) view.findViewById(R.id.row_method);
        TextView upvotesView = (TextView) view.findViewById(R.id.row_upvotes);amountView.setText("" + donation.amount);
        methodView.setText(donation.method);
        upvotesView.setText("" + donation.upvotes);
        view.setTag(donation.id); // setting the 'row' id to the id of the donation
        return view;
    }
    @Override
    public int getCount() {
        return donations.size();
    }
}