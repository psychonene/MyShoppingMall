package com.nana.myshoppingmall.myshoppingmall.db;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by user on 22/07/2016.
 */
public class CartItem extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private String image;
    private double price;
    private int qty;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
