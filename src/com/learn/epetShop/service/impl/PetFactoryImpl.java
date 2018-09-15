package com.learn.epetShop.service.impl;

import com.learn.epetShop.entity.Pet;
import com.learn.epetShop.service.PetFactory;

/**
 * @program: eptshop
 * @description: 宠物工厂实现类
 * @author Mr.Xie
 * @create 2018-09-12 15:00
 **/
public class PetFactoryImpl implements PetFactory {

    @Override
    /**
     * 培育新宠物(创建新宠物)
     */
    public Pet breadNewPet(String[] petParam) {
        Pet pet = new Pet();
        pet.setName(petParam[0]);
        pet.setTypeName(petParam[1]);
        pet.setHealth(Integer.parseInt(petParam[2]));
        pet.setLove(Integer.parseInt(petParam[3]));
        pet.setStoreId(Integer.parseInt(petParam[4]));
        return pet;
    }
}
