package com.learn.epetShop.entity;

/**
 * @program: eptshop
 * @description: 宠物主人类
 * @author: Mr.Xie
 * @create: 2018-09-11 18:35
 **/
public class PetOwner {
    private int id;
    private String name;
    private String password;
    private double money;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public double getMoney() {
        return money;
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

    public void setMoney(double money) {
        this.money = money;
    }
}
