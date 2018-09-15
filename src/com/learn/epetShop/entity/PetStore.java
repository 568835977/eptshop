package com.learn.epetShop.entity;

/**
 * @program: eptshop
 * @description: 宠物商店
 * @author Mr.Xie
 * @create: 2018-09-11 18:37
 **/
public class PetStore {
    private int id;
    private String name;
    private String password;
    private double balance;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
