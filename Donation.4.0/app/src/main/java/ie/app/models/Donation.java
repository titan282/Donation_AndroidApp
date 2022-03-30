package ie.app.models;

public class Donation {
    public int amount;
    public String method;
    public int id;
    public Donation(){
        amount=0;
        method="";
    }
    public Donation(int amount, String method) {
        this.amount = amount;
        this.method = method;
    }

    @Override
    public String toString() {
        return id + ", " +amount+", "+method;
    }
}
