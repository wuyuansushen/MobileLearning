package com.wwl.mobilelearning;

import java.io.Serializable;

public class Transaction implements Serializable {
    private int typeID;
    private String name;
    private Double price;

    //构造函数
    public Transaction(int typeID,String name,Double price){
        this.typeID=typeID;
        this.name=name;
        this.price=price;
    }

    //getter and setter
    public int getTypeID(){return this.typeID;}
    public String getName(){return this.name;}
    public Double getPrice(){return this.price;}

    public void setTypeID(int typeID){this.typeID=typeID;}
    public void setName(String name){this.name=name;}
    public void setPrice(Double price){this.price=price;}
}
