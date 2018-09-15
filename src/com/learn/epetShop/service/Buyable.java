package com.learn.epetShop.service;

import com.learn.epetShop.entity.Pet;

/**
 * 购买宠物接口
 */
public interface Buyable {
    /**
     * 宠物主人购买库存宠物
     */
    public void buy(Pet pet);
}
