package com.learn.epetShop.entity;

import java.util.Date;

/**
 * @program: eptshop
 * @description: 宠物类
 * @author: Mr.Xie
 * @create: 2018-09-11 16:45
 **/
public class Pet {
    private int id;
    private String name;
    private String typeName;
    private int health;
    private int love;
    private Date birthday;
    private int ownerId;
    private int storeId;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getHealth() {
        return health;
    }

    public int getLove() {
        return love;
    }

    public Date getBirthday() {
        return birthday;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getStoreId() {
        return storeId;
    }
}
