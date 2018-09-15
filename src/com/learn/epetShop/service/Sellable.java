package com.learn.epetShop.service;

import com.learn.epetShop.entity.Pet;

/**
 * 宠物售卖接口
 */
public interface Sellable {
    /**
     * 卖宠物
     * @param pet 宠物
     */
    public void sell(Pet pet);
}
