package ie.app.models;

public class Donation {
    public int amount;
    public String method;
    public int id;
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
