package com.ryantroxler.shottystash;

/**
 * Created by ryantroxler on 7/3/17.
 */

public class ObjectShotgun {
    int id;
    String name;
    String image_url;
    Double price;

    public ObjectShotgun() {}
    public ObjectShotgun(String n, String url, Double p) {
        name = n;
        image_url = url;
        price = p;
    }
    public ObjectShotgun(int i, String n, String url, Double p) {
        id = i;
        name = n;
        image_url = url;
        price = p;
    }
}

