package com.learn.epetShop.entity;

import java.util.Date;

/**
 * @program: eptshop
 * @description: 宠物商店台账类
 * @author: Mr.Xie
 * @create: 2018-09-11 18:38
 **/
public class Account {
    private long id;
    private int dealType;
    private long petId;
    private long sellerId;
    private long buyerId;
    private double price;
    private Date dealTime;

    public void setId(long id) {
        this.id = id;
    }

    public void setDealType(int dealType) {
        this.dealType = dealType;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public long getId() {
        return id;
    }

    public int getDealType() {
        return dealType;
    }

    public long getPetId() {
        return petId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public double getPrice() {
        return price;
    }

    public Date getDealTime() {
        return dealTime;
    }
}
