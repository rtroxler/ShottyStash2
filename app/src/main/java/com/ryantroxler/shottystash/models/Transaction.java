package com.ryantroxler.shottystash.models;

import java.util.Date;

/**
 * Created by ryantroxler on 7/9/17.
 */

public class Transaction {
    int id;
    Double amount;
    Date date;

    public Transaction() {}
    public Transaction(Double amount) {
        this.amount = amount;
        this.date = new Date();
    }

    public Double getAmount() {
        return this.amount;
    }

    public Date getDate() {
        return this.date;
    }
}
