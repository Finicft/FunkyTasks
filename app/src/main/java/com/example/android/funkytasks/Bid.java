package com.example.android.funkytasks;

/**
 * Created by jimi on 2018-02-21.
 */

public class Bid {

    //title,bid amount,bidder
    private User bidder;
    private double amount;

    //constracutor for bid
    public Bid(User bidder,Double amount){
        this.bidder=bidder;
        this.amount=amount;
    }


    //methods for bid
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    public User getBidder() {
        return bidder;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }


}