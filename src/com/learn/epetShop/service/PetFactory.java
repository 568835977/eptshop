package com.learn.epetShop.service;

import com.learn.epetShop.entity.Pet;

/**
 * 宠物工厂接口
 */
public interface PetFactory {
    /**
     * 培育新宠物
     * @param petParam
     * @return
     */
    public Pet breadNewPet(String[] petParam);
}
