package com.learn.epetShop.service;


import com.learn.epetShop.entity.Pet;

/**
 * 宠物培育接口
 */
public interface Breadable {
    /**
     * 宠物繁育
     * @param petType 宠物类型
     * @return Pet类
     */
    public Pet bread(String petType);
}
