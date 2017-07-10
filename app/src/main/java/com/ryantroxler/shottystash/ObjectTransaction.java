package com.ryantroxler.shottystash;

import java.util.Date;

/**
 * Created by ryantroxler on 7/9/17.
 */

public class ObjectTransaction {
    int id;
    Double amount;
    Date date;

    public ObjectTransaction() {}
    public ObjectTransaction(Double amount) {
        this.amount = amount;
        this.date = new Date();
    }
}
