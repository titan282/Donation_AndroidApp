package ie.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Donation {
    public int amount;
    public String method;
    public String id;
    public int upvotes;
    public Donation(){
        amount=0;
        method="";
        upvotes=0;
    }
    public Donation(int amount, String method,int upvotes) {
        this.amount = amount;
        this.method = method;
        this.upvotes = upvotes;
    }
    @Override
    public String toString() {
        return id + ", " +amount+", "+method+", "+upvotes;
    }
}
